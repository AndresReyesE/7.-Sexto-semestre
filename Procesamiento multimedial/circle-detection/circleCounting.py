import cv2 as cv
import math
import numpy as np
from functools import reduce
import copy
import imgFileUtils as utils

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
    relativePath = 'imagenes/'

    number_of_filters = 4
    band = '1'


    # for band in range (1, 8):
    # print("Starting band {}".format(band))

    for folder in zip(range(13), folders):
        # print("Starting folder {}".format(folder[0] + 1))
        path = relativePath + folder[1] + '/TOA/' + folder[1] + "_b" + band
        observations = individualObservations(F[folder[0]], actualObservations)
        image = cv.imread(path + '.png', 0)
        bestFscore = bestBlur = bestBlurP = bestSharp = bestContrast = bestThreshold = bestAcc = circlesInBest = bestTP = bestFP = bestFN = 0

        for bluur in range (4):

            for blur_param in [3, 5]:

                if bluur == 0:
                    blurred = copy.copy(image)
                elif bluur == 1:
                    blurred = cv.medianBlur(image, blur_param)
                elif bluur == 2:
                    blurred = cv.GaussianBlur(image, (blur_param, blur_param), 0)
                elif bluur == 3:
                    blurred = cv.bilateralFilter(image, blur_param, blur_param, blur_param)
                
                for sharp in [0, 9]:
                    if sharp == 0:
                        sharped = copy.copy(blurred)
                    else:
                        kern = np.array([[-1, -1, -1], [-1, sharp, -1], [-1, -1, -1]])
                        sharped = cv.filter2D(blurred, -1, kern)

                    for contrastt in [0, 10, 20]:
                        contrasted = utils.contrast(sharped, contrastt)

                        for threshold in range(1, 18):
                            for accumulator in range(22, 27):
                                try:
                                    circles = cv.HoughCircles(contrasted, cv.HOUGH_GRADIENT, 1, 20, param1=threshold, param2=accumulator, minRadius=10, maxRadius=20)
                                    TP = FP = FN = 0
                                    for circle in circles[0, :]:
                                        if positive(circle[:-1], observations):
                                            TP += 1
                                        else:
                                            FP += 1

                                    # for observation in observations:
                                    #     if (not positive(observation, circles[0][:])):
                                    #         FN += 1
                                    FN = len(observations) - TP

                                    # if (TP == 0):
                                    amountOfCircles = circles.size / 3
                                    precision, recall, fscore = get_metric(TP, FP, FN)

                                    if (fscore > bestFscore):
                                        bestFscore = fscore
                                        bestBlur = bluur
                                        bestBlurP = blur_param
                                        bestSharp = sharp
                                        bestContrast = contrastt
                                        bestThreshold = threshold
                                        bestAcc = accumulator
                                        circlesInBest = amountOfCircles
                                        bestTP = TP
                                        bestFP = FP
                                        bestFN = FN

                                    # if (fscore >= 0.8 and fscore < 1.0):
                                        # print("[{}]F-Score hit! {} circles at threshold {}, accumulator {}, with filter {}. F-Score: {:12.2f}".format(band_i[1], amountOfCircles, threshold, accumulator, filteer, fscore))

                                except:
                                    print("", end="")
                                    # print("Exception at threshold " + str(threshold) + " and accumulator " + str(accumulator))
                        # print("Done with threshold and accumulator for this iteration")

        if (bestFscore > 0):
            # if bestFilter == 0:
            #     improved = copy.copy(image)
            # elif bestFilter == 1:
            #     improved = cv.medianBlur(image, 5)
            # elif bestFilter == 2:
            #     improved = cv.GaussianBlur(image, (5, 5), 0)
            # elif bestFilter == 3:
            #     improved = copy.copy(image)
            #     improved = cv.medianBlur(improved, 5)
            #     improved[image < 255-50] += 50
            # elif bestFilter == 4:
            #     improved = copy.copy(image)
            #     improved = cv.medianBlur(improved, 5)
            #     improved[image < 255-100] += 100

            # circles = cv.HoughCircles(improved, cv.HOUGH_GRADIENT, 1, 20, param1=bestThreshold, param2=bestAcc, minRadius=10, maxRadius=20)
            
            # sketch = cv.cvtColor(improved, cv.COLOR_GRAY2BGR)
            
            # for c in circles[0, :]:
            #     cv.circle(sketch, (c[0], c[1]), c[2], (0, 255, 0), 2)
            #     cv.circle(sketch, (c[0], c[1]), 2, (0, 0, 255), 3)
            
            # print("Maximum for [{}]: {} circles at threshold {}, accumulator {}, with filter: Blur {} | BP = {} | Sharp = {} | Contr = {} |. TP = {}, FP = {}. FN = {}, F-Score: {}\a".format(folder[1], circlesInBest, bestThreshold, bestAcc, bestBlur, bestBlurP, bestSharp, bestContrast, bestTP, bestFP, bestFN, bestFscore))
            print("{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}".format(folder[0] + 1, circlesInBest, bestThreshold, bestAcc, bestBlur, bestBlurP, bestSharp, bestContrast, bestTP, bestFP, bestFN, bestFscore))
            # cv.imshow("{} circles at threshold {}, accumulator {}, with filter {}. F-Score: {:12.2f}".format(circlesInBest, bestThreshold, bestAcc, bestFilter, bestFscore), sketch)
            # cv.waitKey(0)
            # cv.destroyAllWindows()





        # for filteer in range(number_of_filters):
        #     if filteer == 0:
        #         improved = copy.copy(image)
        #     elif filteer == 1:
        #         improved = utils.contrast(image, 10)
        #     elif filteer == 2:
        #         improved = utils.contrast(image, 20)
        #         # improved = cv.GaussianBlur(image, (3, 3), 3)
        #     elif filteer == 3:
        #         improved = utils.contrast(image, 50)
                # kern = np.array([[-1, -1, -1], [-1, 11, -1], [-1, -1, -1]])
                # improved = cv.filter2D(image, -1, kern)
                # improved = utils.contrast(image, 10)
                # improved = cv.GaussianBlur(image, (5, 5), 0)
            # elif filteer == 4:


def applyCanny(filename, threshold):
    img = cv.imread(filename, 0)

    if str(img) == "None":
        raise FileNotFoundError("Specified image file doesnt exist!")

    cimg = cv.cvtColor(img, cv.COLOR_GRAY2BGR)

    edges = cv.Canny(cimg, threshold, threshold/2, 3)

    return edges


def positive(point, ls):
    x, y = point
    m = list(map((lambda observation: int(x) in range(int(observation[0]) - 5, int(observation[0] + 5)) and int(y) in range (int(observation[1]) - 5, int(observation[1]) + 5)), ls))
    r = reduce((lambda a, b: a or b), m)
    return r


def get_metric(TP, FP, FN):
    precision = TP / (TP + FP)  
    recall = TP / (TP + FN)
    fscore = 2 * (recall * precision) / (recall + precision) if TP > 0 else 0
    return [precision, recall, fscore]


def individualObservations(F, ls):
    newList = []
    for i in F:
        newList.append(ls[i])
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
