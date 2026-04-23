import os
import random
import cv2
import numpy as np
from tensorflow.keras.models import load_model

MODEL_PATH = "ai-model/my_model_simple_v2.h5"
DATASET_PATH = "ai-model/dataset"

model = load_model(MODEL_PATH)

def predict_image(image_path):
    img = cv2.imread(image_path)

    if img is None:
        return "ERROR", 0.0

    img = cv2.resize(img, (256, 256))
    img = img / 255.0
    img = np.reshape(img, (1, 256, 256, 3))

    pred = model.predict(img, verbose=0)
    confidence = float(pred[0][0])

    if confidence > 0.5:
        return "tumor", confidence
    else:
        return "no_tumor", 1 - confidence


correct = 0
total = 0

for actual_label in ["tumor", "no_tumor"]:
    folder_path = os.path.join(DATASET_PATH, actual_label)

    print(f"\nChecking folder: {actual_label}")

    all_files = os.listdir(folder_path)
    files = random.sample(all_files, 20)

    for file_name in files:
        image_path = os.path.join(folder_path, file_name)

        predicted_label, confidence = predict_image(image_path)

        print(f"Image: {file_name} | Predicted: {predicted_label} | Confidence: {confidence:.4f}")

        if predicted_label == actual_label:
            correct += 1

        total += 1

print("\nSummary")
print(f"Correct: {correct}")
print(f"Total: {total}")
print(f"Accuracy: {correct / total:.2%}")
