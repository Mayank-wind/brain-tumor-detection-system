import numpy as np
import cv2
from tensorflow.keras.models import load_model
import sys
import os

img_path = sys.argv[1]
# Load model safely
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
model_path = os.path.join(BASE_DIR, "model.h5")
model = load_model(model_path)
# Read image
img = cv2.imread(img_path)

if img is None:
    print("ERROR")
    exit()
img = cv2.resize(img, (256, 256))
img = img / 255.0
img = np.reshape(img, (1, 256, 256, 3))

pred = model.predict(img, verbose=0)

confidence = float(pred[0][0])

if confidence > 0.5:
    print(f"TUMOR:{confidence}")
else:
    print(f"NO_TUMOR:{1 - confidence}")