import cv2 as cv


image = cv.imread('image.png')

image = cv.resize(image, None, fx=0.5, fy=0.5, interpolation=cv.INTER_LINEAR)



flags = [cv.COLOR_BGR2GRAY, cv.COLOR_BGR2YUV, cv.COLOR_BGR2XYZ, cv.COLOR_BGR2HSV]
names = ["RGB (BGR)", "GRAYSCALE", "YUV (YCbCr)", "CIE (XYZ)", "HSV"]
images =[image]
channelsNames = [[" BLUE CHANNEL", " GREEN CHANNEL", " RED CHANNEL"],
[],
[" Y GRAYSCALE", " U Cb CHANNEL", " U Cr CHANNEL"], 
[" BLUE CHANNEL", " GREEN CHANNEL", " RED CHANNEL"],
[" HUE CHANNEL", " SATURATION CHANNEL", " VALUE CHANNEL"]]


for i in flags:
    images.append(cv.cvtColor(image, i))
    
for i in zip(names, images, channelsNames):
    cv.imshow(i[0], i[1])
    if i[0] != "GRAYSCALE":
        channels = cv.split(i[1])
        for j in zip(channels, i[2]):
            cv.imshow(i[0] + j[1], j[0])
    cv.waitKey(0)
    cv.destroyAllWindows()

cv.imwrite("img.jpg", image, [cv.IMWRITE_JPEG_QUALITY, 95])
