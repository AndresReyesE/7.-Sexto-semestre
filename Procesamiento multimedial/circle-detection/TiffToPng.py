import circleCounting as circles
import imgFileUtils as utils

def main():
    relativePath = 'imagenes/'

    folders = ["LC080330422013062201T1", "LC080330422014042201T1",
               "LC080330422015010301T1", "LC080330422015111901T1",
               "LC080330422017061701T1", "LE070330422002010701T1",
               "LT050330421995041801T1", "LT050330421999112301T1",
               "LT050330422000011001T1", "LT050330422003040801T1",
               "LT050330422004061301T1", "LT050330422005110701T1",
               "LT050330422010012101T1"]

    for band in range(1, 8):
        band = str(band)
        print("Starting band {}".format(band))
        for band_i in zip(range(13), folders):
            print("Beggining Folder {}".format(band_i[0] + 1))
            path = relativePath + band_i[1] + '/TOA/' + band_i[1] + "_b" + band
            utils.writeTiffToPng(path + '.tif')

if __name__ == '__main__':
    main()