import circleCounting as circles
import imgFileUtils as utils
import cv2

def main():
    actualObservations = circles.getActualObservations()

    relativePath = 'imagenes/'

    folders = ["LC080330422013062201T1", "LC080330422014042201T1",
               "LC080330422015010301T1", "LC080330422015111901T1",
               "LC080330422017061701T1", "LE070330422002010701T1",
               "LT050330421995041801T1", "LT050330421999112301T1",
               "LT050330422000011001T1", "LT050330422003040801T1",
               "LT050330422004061301T1", "LT050330422005110701T1",
               "LT050330422010012101T1"]


    for band_i in zip(range(13), folders):
        print("Beggining Folder {}".format(band_i[0] + 1))
        pathRed = relativePath + band_i[1] + '/TOA/' + band_i[1] + "_b4"
        pathGreen = relativePath + band_i[1] + '/TOA/' + band_i[1] + "_b3"
        pathBlue = relativePath + band_i[1] + '/TOA/' + band_i[1] + "_b2"

        red = cv2.imread(pathRed + ".png", 0)
        green = cv2.imread(pathGreen + ".png", 0)
        blue = cv2.imread(pathGreen + ".png", 0)

        rgb = utils.joinRGBFrequencies(red, green, blue)

        cv2.imwrite(relativePath + band_i[1] + '/TOA/' + band_i[1] + "_b8.png", rgb)

        #utils.writeTiffToPng(path + '.tif')

if __name__ == '__main__':
    main()