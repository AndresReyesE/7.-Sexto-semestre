import cv2
import numpy as np
import math
import copy

def joinRGBFrequencies(red, green, blue):
    if red.size == green.size and blue.size:
        height, width = red.shape

        # rgbImg = cv2.merge((blue,green,red))
        rgbImg = cv2.cvtColor(red, cv2.COLOR_GRAY2RGB)

        for i in range(height):
            for j in range(width):
                rgbImg[i][j] = [blue[i][j], green[i][j], red[i][j]]
        return rgbImg
    else:
        raise AttributeError


def writeTiffToPng(img):
    print("Converting " + img + " to PNG...")
    read = cv2.imread(img, cv2.IMREAD_ANYDEPTH)
    out = cv2.imread(img, cv2.IMREAD_ANYDEPTH)

    height = int(read.shape[0])
    width = int(read.shape[1])

    for i in range (0, height):
        for j in range (0, width):
            out[i][j] = math.floor(255 * read[i][j])

    cv2.imwrite(removeExtension(img, 4) + ".png", out)
    print("Succesfully converted " + img + " to PNG.")
    print("Saved in: " + removeExtension(img, 4) + ".png")


def removeExtension(filename, leng):
    filename = filename[0:len(filename) - leng]
    return filename


def contrast (img, value):
    buf = copy.copy(img)
    if value != 0:
        f = 131 * (value + 127) / (127 * (131 - value))
        gamma = 127 * (1 - f)

        buf = cv2.addWeighted(buf, f, buf, 0, gamma)

    return buf