import cv2 as cv
import numpy as np
from functools import reduce

original = cv.imread('cameraman.jpg')
bn = cv.cvtColor(original, cv.COLOR_BGR2GRAY)

rows, cols = bn.shape

A = 1

mask1 = [-1, -1, -1, -1, A + 8, -1, -1, -1, -1]
mask2 = [0, -1, 0, -1, (A + 4), -1, 0, -1, 0]

def getNeighborhood (i, j):
    coordinates = map (lambda x: zip([x] * 3, range(j-1, j+2)), range(i-1, i+2))
    return [item for sublist in coordinates for item in sublist]


def applyMask (image, mask, i, j):
    currentNeighborhoodCoordinates = getNeighborhood(i, j)
    currentNeighborhood = map (lambda x: image.item (x[0], x[1]), currentNeighborhoodCoordinates)
    maskApplied         = map (lambda x, y: x * y, currentNeighborhood, mask)
    result              = reduce (lambda x, y: x + y, maskApplied) / 9
    return result


#a
#for i in range(1, rows-1):
#    for j in range(1, cols-1):
#        result = applyMask(bn, mask2, i, j)
        #if (result < 0):
#        bn.itemset((i, j), bn.item(i, j) - result)
        #else:
#        bn.itemset((i, j), bn.item(i, j) - result)

#b
#for i in range (rows):
#    for j in range (cols):
#        currentNeighborhoodCoordinates = getNeighborhood (i, j)
#        currentNeighborhood = map (lambda x: 0 if (x[0] < 0 or x[1] < 0 or x[0] >= rows or x[1] >= cols) else bn.item (x[0], x[1]), currentNeighborhoodCoordinates)
#        maskApplied         = map (lambda x, y: x * y, currentNeighborhood, mask1)
#        result              = reduce (lambda x, y: x + y, maskApplied) / 9
#        if (result < 0):
#            bn.itemset((i, j), bn.item(i, j) - result)
#        else:
#            bn.itemset((i, j), bn.item(i, j) + result)

def addsubs1 (i):
    if i != 0:
        i -= i / abs(i)
    return i

def correctOutOfBounds (tupl, rows, cols):
    i, j = tupl
    #print("i = " + str(i) + ", j = " + str(j))
    if i not in range (rows):
        i = addsubs1(i)
    if j not in range (cols):
        j = addsubs1(j)
    return (int(i), int(j))

for i in range (rows):
    for j in range (cols):
        currentNeighborhoodCoordinates = getNeighborhood (i, j)
        currentNeighborhoodCoordinatesCorrected = map (lambda x: x if (x[0] in range (rows) and x[1] in range (cols)) else correctOutOfBounds(x, rows, cols), currentNeighborhoodCoordinates)
        currentNeighborhood = map (lambda x: bn.item (x[0], x[1]), currentNeighborhoodCoordinatesCorrected)
        maskApplied         = map (lambda x, y: x * y, currentNeighborhood, mask1)
        result              = reduce (lambda x, y: x + y, maskApplied) / 9
        bn.itemset((i, j), bn.item(i, j) - result)

            
print("")   

cv.imshow("original", original)
cv.imshow("bn", bn)
#cv.imshow("cp", cp)
cv.waitKey(0)
cv.destroyAllWindows()