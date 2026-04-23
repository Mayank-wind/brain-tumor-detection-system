import argparse
import os
import sys

import cv2
import numpy as np
from tensorflow.keras.models import load_model


def main():
    parser = argparse.ArgumentParser(description="Run prediction on one MRI image.")
    parser.add_argument("image_path", help="Path to image file")
    parser.add_argument(
        "--model-path",
        default=os.path.join(os.path.dirname(os.path.abspath(__file__)), "my_model_simple_v2.h5"),
        help="Path to trained model file",
    )
    args = parser.parse_args()

    model = load_model(args.model_path)
    img = cv2.imread(args.image_path)

    if img is None:
        print("ERROR")
        sys.exit(1)

    img = cv2.resize(img, (256, 256))
    img = img / 255.0
    img = np.reshape(img, (1, 256, 256, 3))

    pred = model.predict(img, verbose=0)
    confidence = float(pred[0][0])

    if confidence > 0.5:
        print(f"TUMOR:{confidence}")
    else:
        print(f"NO_TUMOR:{1 - confidence}")


if __name__ == "__main__":
    main()
