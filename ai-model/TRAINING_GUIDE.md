# Binary Model Training Guide

This guide trains your own `tumor` vs `no_tumor` model without replacing the current `model.h5`.

## Dataset structure

Your dataset should look like this:

```text
ai-model/
  dataset/
    tumor/
    no_tumor/
```

## Install dependencies

```powershell
pip install -r ai-model/requirements.txt
```

## Train your own model

```powershell
python ai-model/train_model.py --epochs 15 --batch-size 16
```

This will create:

- `ai-model/my_model.h5`
- `ai-model/training_history_binary.json`
- `ai-model/class_names_binary.json`

## Why this works

- The script reads folder names as labels.
- It automatically uses 80% of images for training and 20% for validation.
- Images are resized to `256x256`.
- Pixel values are normalized from `0-255` to `0-1`.
- The last layer uses `sigmoid`, which is standard for binary classification.

## What to look at after training

- Training accuracy
- Validation accuracy
- Training loss
- Validation loss

If validation accuracy improves and validation loss goes down, the model is learning useful patterns.

## Important

This script saves to `my_model.h5`, not `model.h5`, so your current project remains safe.
