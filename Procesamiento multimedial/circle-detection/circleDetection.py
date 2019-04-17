import cv2
import numpy as np
import math


def main():
    currentPath = '/home/reyes/7.-Sexto-semestre/WIP/circle-detection/imagenes/LT050330422010012101T1/TOA/'
    currentImageSetName = 'LT050330422010012101T1_b'
    extension = '.tif'

    bands = []

    for i in range (1, 8):
        path = currentPath + currentImageSetName + str(i) + extension
        bands.append(cv2.imread(path, cv2.IMREAD_ANYDEPTH))

    threshold = 1


    for i in zip(bands, range (1, 8)):
        path = currentPath + currentImageSetName + str(i[1]) + extension
        writeTiffToPng(path)
        edges = applyCanny(path, threshold)
        img = detectCirclesTIFF(path, threshold)
        cv2.imshow("Band " + str(i[1]), i[0])
        cv2.imshow("Edges for band " + str(i[1]), edges)
        cv2.imshow("Detected circles for band " + str(i[1]), img)
        # cv2.imwrite(currentPath + currentImageSetName + str(i[1]) + "-.tif", i[0])
        cv2.waitKey(0)
        cv2.destroyAllWindows()
    



def removeExtension(filename, leng):
    filename = filename[0:len(filename) - leng]
    return filename


def detectCirclesTIFF(filename, threshold):
    print("Reading image...")
    img = cv2.imread(removeExtension(filename, 4) + ".png", 0)

    if str(img) == "None":
        raise FileNotFoundError("Specified image file doesnt exist!")


    # print("Applying blur...")
    # #img = cv2.medianBlur(img,1)


    # print("Writing blurred image...")
    # cv2.imwrite("blurred.png", img)

    cimg = cv2.cvtColor(img, cv2.COLOR_GRAY2BGR)
    #cimg = cv2.imread("blurred.png", img)
    #cimg = cv2.cvtColor(cimg, cv2.COLOR_GRAY2BGR)

    print("Detecting circles")
    circles = cv2.HoughCircles(img, cv2.HOUGH_GRADIENT, 1, 20, param1=threshold, param2=26, minRadius=10, maxRadius=20)
    circles = np.uint16(np.around(circles))

    print("detected circles: " + str(circles.size / 3))

    for i in circles[0, :]:
        # draw the outer circle
        cv2.circle(cimg,(i[0],i[1]),i[2],(0,255,0),2)
        # draw the center of the circle
        cv2.circle(cimg,(i[0],i[1]),2,(0,0,255),3)
    print("Writing final image...")
    cv2.imwrite(removeExtension(filename, 4) + "'.png", cimg)
    print("Image written")

    return cimg

def applyCanny (filename, threshold):
    print("Reading image...")
    img = cv2.imread(removeExtension(filename, 4) + ".png", 0)
    
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


if __name__ == "__main__":
    main()




