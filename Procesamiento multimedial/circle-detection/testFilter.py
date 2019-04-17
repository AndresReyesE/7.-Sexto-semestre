import cv2 as cv
import numpy as np
import math
from functools import reduce
import copy

folders = ["LC080330422013062201T1", "LC080330422014042201T1", "LC080330422015010301T1", "LC080330422015111901T1", "LC080330422017061701T1", "LE070330422002010701T1", "LT050330421995041801T1", "LT050330421999112301T1", "LT050330422000011001T1", "LT050330422003040801T1", "LT050330422004061301T1", "LT050330422005110701T1", "LT050330422010012101T1"]
currentPath = '/home/reyes/7.-Sexto-semestre/WIP/circle-detection/imagenes/'
relativePath = 'imagenes/'
extension = '.tif'
band = '5'

def brightness (img, value):
    newImg = copy.copy(img)
    height, width = newImg.shape
    for i in range (height):
        for j in range (width):
            newImg[i][j] += value

def contrast (img, value):
    buf = copy.copy(image)
    if value != 0:
        f = 131 * (value + 127) / (127 * (131 - value))
        gamma = 127 * (1 - f)

        buf = cv.addWeighted(buf, f, buf, 0, gamma)

    return buf

def increase_brightness(img, value=30):
    newImg = copy.copy(img)
    hsv = cv.cvtColor(newImg, cv.COLOR_BGR2HSV)
    h, s, v = cv.split(hsv)

    lim = 255 - value
    v[v > lim] = 255
    v[v <= lim] += value

    final_hsv = cv.merge((h, s, v))
    newImg = cv.cvtColor(final_hsv, cv.COLOR_HSV2BGR)
    return newImg

for folder in folders:
    path = relativePath + folder + '/TOA/' + folder + '_b' + band + '.png'
    brt = 40

    image = cv.imread(path, 0)

    improved = copy.copy(image)
    # improved = cv.GaussianBlur(image, (5, 5), 0)
    # improved = cv.medianBlur(image, 5)
    # improved[image < 255-brt] += brt
    # improved = contrast(image, 0)
    improved = cv.bilateralFilter (image, 5, (5, 5))
    # improved = cv.applyColorMap(image, cv.COLORMAP_HSV)

    circles = cv.HoughCircles(improved, cv.HOUGH_GRADIENT, 1, 20, param1=30, param2=24, minRadius=10, maxRadius=20)
    # sketch = cv.cvtColor(improved, cv.COLOR_GRAY2BGR)

    # if circles is not None: 
    # for circle in circles[0, :]:
    #     cv.circle (sketch, (circle[0], circle[1]), circle[2], (0, 255, 0), 2)
    #     cv.circle (sketch, (circle[0], circle[1]), 2, (0, 0, 255), 3)


    cv.imshow("Original", image)
    cv.imshow("Filter applied", improved)
    # cv.imshow("Circles found", sketch)
    cv.waitKey(0)
    cv.destroyAllWindows()

