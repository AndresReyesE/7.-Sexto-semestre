import cv2 as cv
import numpy as np
import math
from functools import reduce
import copy
import imgFileUtils as utils

bands = [[] for x in range(8)]

bands.__setitem__(0, [1, 25, 0, 3, 9, 0])
bands.__setitem__(1, [1, 26, 0, 3, 9, 10])
bands.__setitem__(2, [4, 23, 3, 3, 0, 0])
bands.__setitem__(3, [1, 26, 1, 3, 0, 0])
bands.__setitem__(4, [1, 24, 2, 5, 0, 10])
bands.__setitem__(5, [1, 25, 0, 3, 9, 0])
bands.__setitem__(6, [1, 25, 2, 5, 0, 0])
bands.__setitem__(7, [1, 25, 0, 3, 0, 10])


def main():
    actualObservations = getActualObservations()
    F = getFolderObservations(actualObservations)


    folders = ["LC080330422013062201T1", "LC080330422014042201T1",
               "LC080330422015010301T1", "LC080330422015111901T1",
               "LC080330422017061701T1", "LE070330422002010701T1",
               "LT050330421995041801T1", "LT050330421999112301T1",
               "LT050330422000011001T1", "LT050330422003040801T1",
               "LT050330422004061301T1", "LT050330422005110701T1",
               "LT050330422010012101T1"]
    currentPath = '/home/reyes/7.-Sexto-semestre/WIP/circle-detection/imagenes/'
    relativePath = 'imagenes/'
    extension = '.tif'


    for band in zip(range(1, 9), bands):
        # print("Band " + str(band[0]))
        for folder in zip(range (13), folders):
            observations = individualObservations(F[folder[0]], actualObservations)
            # print("Folder " + str(folder[0] + 1))
            path = relativePath + folder[1] + '/TOA/' + folder[1] + "_b" + str(band[0])
            image = cv.imread(path + '.png', 0)
            threshold, accumulator, bluur, blur_param, sharp, contrastt = band[1]

            if bluur == 0:
                blurred = copy.copy(image)
            elif bluur == 1:
                blurred = cv.medianBlur(image, blur_param)
            elif bluur == 2:
                blurred = cv.GaussianBlur(image, (blur_param, blur_param), 0)
            elif bluur == 3:
                blurred = cv.bilateralFilter(image, blur_param, blur_param, blur_param)

            if sharp == 0:
                sharped = copy.copy(blurred)
            else:
                kern = np.array([[-1, -1, -1], [-1, sharp, -1], [-1, -1, -1]])
                sharped = cv.filter2D(blurred, -1, kern)

            contrasted = utils.contrast(sharped, contrastt)
        
            try:
                circles = cv.HoughCircles(contrasted, cv.HOUGH_GRADIENT, 1, 20, param1=threshold, param2=accumulator, minRadius=10, maxRadius=20)
                TP = FP = FN = 0
                sketch = cv.cvtColor(contrasted, cv.COLOR_GRAY2BGR)
                for circle in circles[0, :]:
                    
                    cv.circle (sketch, (circle[0], circle[1]), circle[2], (0, 255, 0), 2)

                    cv.circle (sketch, (circle[0], circle[1]), 2, (0, 0, 255), 3)

                    if positive(circle[:-1], observations):
                        TP += 1
                    else:
                        FP += 1

                FN = len(observations) - TP
                amountOfCircles = circles.size / 3
                precision, recall, fscore = get_metric(TP, FP, FN)
                # print("Valors: {} circles at threshold {}, accumulator {}, with blur {} - {}, sharp {} and contrast {}. F-Score: {:12.2f}".format(amountOfCircles, threshold, accumulator, bluur, blur_param, sharp, contrastt, fscore))
                print("{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}".format(
                    band[0], folder[0] + 1, amountOfCircles, threshold, accumulator, bluur, blur_param, sharp, contrastt, TP, FP, FN, fscore))
                # cv.imshow("Band {}, folder {}: {} circles. Threshold {}, acc {}, blur {}-{}, sharp {}, contrast {}. F-Score: {:12.2f}".format(band[0], folder[0] + 1, amountOfCircles, threshold, accumulator, bluur, blur_param, sharp, contrastt, fscore), sketch)
                # cv.waitKey(0)
                # cv.destroyAllWindows()
            except:
                print("", end="")


def positive (point, ls):
    x, y = point
    m = list (map((lambda observation: int(x) in range (int(observation[0]) - 5, int(observation[0] + 5)) and int(y) in range (int(observation[1]) - 5, int(observation[1]) + 5)), ls))
    r = reduce ((lambda a, b: a or b), m)
    return r

def get_metric (TP, FP, FN):
    precision = TP / (TP + FP)  
    recall = TP / (TP + FN)
    fscore = 2 * (recall * precision) / (recall + precision) if TP > 0 else 0
    return [precision, recall, fscore]

def individualObservations(F, ls):
    newList = []
    for i in F:
        newList.append(ls[i - 1])
    return newList


def getFolderObservations(actualObservations):

    folders = [[] for x in range(13)]

    folders.__setitem__(0, list(range(14)))

    folders.__setitem__(1, list(range(12)))
    folders[1].append(22)
    folders[1].append(25)

    folders.__setitem__(2, [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14, 15, 17, 18, 27, 28])

    folders.__setitem__(3, [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 15, 16, 22, 27])

    folders.__setitem__(4, [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 16, 17, 25])

    folders.__setitem__(5, [10, 15, 26, 29])

    folders.__setitem__(7, [0, 14, 18, 19])

    folders.__setitem__(8, [0, 14, 18, 19])

    folders.__setitem__(9, [7, 8, 9, 18, 19, 20])

    folders.__setitem__(10, [0, 19, 20, 23, 26, 30])

    folders.__setitem__(11, [0, 4, 5, 15, 21])

    folders.__setitem__(12, [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 19, 24, 25])

    return folders

def getActualObservations():
     return [[425, 228], [433, 260], [441, 293],
            [449, 324], [420, 339], [480, 311],
            [567, 287], [537, 278], [531, 253],
            [555, 241], [160, 400], [166, 319],
            [253, 716], [669, 631], [303, 401],
            [184, 424], [349, 101], [99, 715],
            [264, 409], [225, 417], [118, 620],
            [358, 370], [333, 272], [133, 653],
            [166, 537], [389, 355], [203, 529],
            [47, 412], [187, 560], [54, 338],
            [586, 261]]

if __name__ == "__main__":
    main()
