import numpy as np
import cv2
from tensorflow.keras.models import load_model
import sys
model = load_model("model.h5")
img_path = sys.argv[1]
img = cv2.imread(img_path)
img = cv2.resize(img, (256, 256))
img = img / 255.0
img = np.reshape(img, (1, 256, 256, 3))

pred = model.predict(img)
if pred[0][0] > 0.5:
    print("Tumor Detected 🤦‍♀️")
else:
    print("No Tumor 😚")