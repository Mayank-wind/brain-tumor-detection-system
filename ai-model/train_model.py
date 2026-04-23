import argparse
import json
import os

import tensorflow as tf
from tensorflow.keras import layers, models
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint


IMAGE_SIZE = (256, 256)
SEED = 42


def create_datasets(dataset_dir, batch_size):
    train_ds = tf.keras.utils.image_dataset_from_directory(
        dataset_dir,
        validation_split=0.2,
        subset="training",
        seed=SEED,
        image_size=IMAGE_SIZE,
        batch_size=batch_size,
        label_mode="binary",
    )

    val_ds = tf.keras.utils.image_dataset_from_directory(
        dataset_dir,
        validation_split=0.2,
        subset="validation",
        seed=SEED,
        image_size=IMAGE_SIZE,
        batch_size=batch_size,
        label_mode="binary",
    )

    normalization = layers.Rescaling(1.0 / 255)

    train_ds = train_ds.map(lambda x, y: (normalization(x), y)).prefetch(tf.data.AUTOTUNE)
    val_ds = val_ds.map(lambda x, y: (normalization(x), y)).prefetch(tf.data.AUTOTUNE)

    return train_ds, val_ds


def build_model():
    model = models.Sequential(
        [
            layers.Input(shape=(IMAGE_SIZE[0], IMAGE_SIZE[1], 3)),

            layers.Conv2D(32, (3, 3), activation="relu"),
            layers.MaxPooling2D(),

            layers.Conv2D(64, (3, 3), activation="relu"),
            layers.MaxPooling2D(),

            layers.Conv2D(128, (3, 3), activation="relu"),
            layers.MaxPooling2D(),

            layers.Flatten(),

            layers.Dropout(0.4),
            layers.Dense(128, activation="relu"),
            layers.Dropout(0.3),

            layers.Dense(1, activation="sigmoid"),
        ]
    )

    model.compile(
        optimizer="adam",
        loss="binary_crossentropy",
        metrics=["accuracy"],
    )

    return model


def save_training_metadata(output_dir, history):
    history_path = os.path.join(output_dir, "training_history_binary.json")
    labels_path = os.path.join(output_dir, "class_names_binary.json")

    with open(history_path, "w", encoding="utf-8") as file:
        json.dump(history.history, file, indent=2)

    with open(labels_path, "w", encoding="utf-8") as file:
        json.dump(["no_tumor", "tumor"], file, indent=2)


def main():
    parser = argparse.ArgumentParser(description="Train a binary brain tumor detection model.")
    parser.add_argument(
        "--dataset-dir",
        default=os.path.join(os.path.dirname(__file__), "dataset"),
        help="Path to dataset folder containing tumor and no_tumor directories.",
    )
    parser.add_argument(
        "--output-model",
        default=os.path.join(os.path.dirname(__file__), "my_model.h5"),
        help="Path where the trained model will be saved.",
    )
    parser.add_argument("--epochs", type=int, default=15)
    parser.add_argument("--batch-size", type=int, default=16)
    args = parser.parse_args()

    output_dir = os.path.dirname(args.output_model) or "."
    os.makedirs(output_dir, exist_ok=True)

    train_ds, val_ds = create_datasets(args.dataset_dir, args.batch_size)
    model = build_model()

    callbacks = [
        EarlyStopping(
            monitor="val_loss",
            patience=3,
            restore_best_weights=True,
        ),
        ModelCheckpoint(
            args.output_model,
            monitor="val_loss",
            save_best_only=True,
        ),
    ]

    history = model.fit(
        train_ds,
        validation_data=val_ds,
        epochs=args.epochs,
        callbacks=callbacks,
    )

    save_training_metadata(output_dir, history)

    print("Training complete")
    print(f"Saved trained model to: {args.output_model}")


if __name__ == "__main__":
    main()
