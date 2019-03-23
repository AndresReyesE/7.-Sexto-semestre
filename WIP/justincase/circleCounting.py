import cv2
import numpy as np
import math
from functools import reduce

actualObservations = ([425, 228], [433, 260], [441, 293],
[449, 324], [420, 339], [480, 311],
[567, 287], [537, 278], [531, 253],
[555, 241], [160, 400], [166, 319],
[253, 716], [669, 631])

def main():

    currentPath = '/home/reyes/7.-Sexto-semestre/WIP/circle-detection/imagenes/test/'
    currentImageSetName = 'test'
    extension = '.tif'

    for g in range (1, 13):
        path = currentPath + currentImageSetName + str (g)
    #     writeTiffToPng(path + extension)
        image = cv2.imread(path + '.png', 0)
        blur = cv2.medianBlur(image, 1)

        maxx = ii = jj = p = r = ac = 0
        for i in range(1, 30): #threshold
            for j in range (25, 30): #acc
                whitenblack = cv2.cvtColor(image, cv2.COLOR_GRAY2BGR)
                try:    
                    circles = cv2.HoughCircles(blur, cv2.HOUGH_GRADIENT, 1, 20, param1=i, param2=j, minRadius=10, maxRadius=20)
                    TP = FP = FN = 0
                    for k in circles[0, :]:
                        b = positive(k[:-1], actualObservations)
                        if b:
                            TP += 1
                        else:
                            FP += 1
                        cv2.circle(whitenblack,(k[0],k[1]),k[2],(0,255,0),2)
                        cv2.circle(whitenblack,(k[0],k[1]),2,(0,0,255),3)

                    for l in actualObservations:
                        bb = positive(l, circles[0][:])
                        if (not bb):
                            FN += 1
                    amountOfCircles = circles.size / 3
                    precision, recall, fscore = get_metric(TP, FP, FN)

                    


                    # edges = applyCanny(path + '.png', i)
                    # cv2.imshow("Edges for threshold: " + str(i) + ", accumulator: " + str(j), edges)
                    if (fscore > maxx):
                        maxx = fscore
                        ii = i
                        jj = j
                        p = precision
                        r = recall
                        ac = amountOfCircles
                    if ((fscore > 0.8 and fscore < 1.0) or (i == 1 and j == 25)):
                        print (str(amountOfCircles) + " circles detected for a threshold of " + str (i) + " and an acumulator of " + str(j) + "with metrics: {}, {}, {}".format(precision, recall, fscore))
                        # cv2.imshow("(" + str(i) + ", " + str(j) + ") -> " + str (amountOfCircles), whitenblack)
                        # cv2.waitKey(0)
                        # cv2.destroyAllWindows()
                    
                    # if (fscore in (0.8, 1.0)):
                    # if (amountOfCircles in range (11, 18)):
                    


                        
                        # cv2.imshow("Blur", blur)
                except:
                    print("Exception at threshold " + str(i) + " and accumulator " + str(j))
        
        if (ii != 0 or jj != 0):
            whitenblack = cv2.cvtColor(image, cv2.COLOR_GRAY2BGR)
            c = cv2.HoughCircles(blur, cv2.HOUGH_GRADIENT, 1, 20, param1=ii, param2=jj, minRadius=10, maxRadius=20)
            print ("Maximo " + str (g) + ": " + str(ac) + " circles detected for a threshold of " + str (ii) + " and an acumulator of " + str(jj) + "with metrics: {}, {}, {}".format(p, r, maxx))
            for k in circles[0, :]:
                cv2.circle(whitenblack,(k[0],k[1]),k[2],(0,255,0),2)
                cv2.circle(whitenblack,(k[0],k[1]),2,(0,0,255),3)
            cv2.imshow("(" + str(i) + ", " + str(j) + ") -> " + str (amountOfCircles), whitenblack)
            cv2.waitKey(0)
            cv2.destroyAllWindows()
        



def applyCanny (filename, threshold):
    img = cv2.imread(filename, 0)
    
    if str(img) == "None":
        raise FileNotFoundError("Specified image file doesnt exist!")
    
    cimg = cv2.cvtColor(img, cv2.COLOR_GRAY2BGR)

    edges = cv2.Canny(cimg, threshold, threshold/2, 3)

    return edges

def writeTiffToPng(img):
    print("Converting " + img + " to PNG...")
    read = cv2.imread(img, cv2.IMREAD_ANYDEPTH)
    out = cv2.imread(img, cv2.IMREAD_ANYDEPTH)

    height = int(read.shape[0])
    width = int(read.shape[1])

    for i in range (0, height):
        for j in range (0, width):
            out[i][j] = normalize(read[i][j])

    cv2.imwrite(removeExtension(img, 4) + ".png", out)
    print("Succesfully converted " + img + " to PNG.")
    print("Saved in: " + removeExtension(img, 4) + ".png")


def normalize(value):
    pctg = (value * 100)
    normalizedValue = (pctg * 255) / 100
    return math.floor(normalizedValue)

def removeExtension(filename, leng):
    filename = filename[0:len(filename) - leng]
    return filename

def positive (point, ls):
    x, y = point
    m = list (map((lambda observation: int(x) in range (int(observation[0]) - 5, int(observation[0] + 5)) and int(y) in range (int(observation[1]) - 5, int(observation[1]) + 5)), ls))
    r = reduce ((lambda a, b: a or b), m)
    return r

def get_metric (TP, FP, FN):
    precision = TP/(TP+FP)
    recall = TP/(TP+FN)
    fscore = 2*(recall * precision) / (recall + precision)
    return [precision, recall, fscore]


if __name__ == "__main__":
    main()
