#!/bin/bash

#######
# author: obnosim <obnosim@gmail.com>
# version 1.0 (15/06/16)
# Script to regenerate a Yakuake theme
#######

### Functions

is_inkscape_missing() {
	if hash inkscape 2>/dev/null; then
		return 1
	else
		return 0
	fi
}

inkscape_missing() {
    echo "This script requires Inkscape to render images."
	exit 1
}

render_image() {
    inkscape -z --export-background-opacity=0,0 ../source.svg --export-id=$1 --export-png=$1.png >> /dev/null
}

render_image_no_background() {
    inkscape -z --export-background-opacity=0,0 ../source.svg --export-id-only --export-id=$1 --export-png=$1.png >> /dev/null
}

echo ""
if is_inkscape_missing; then
	inkscape_missing
fi

tabs=(back_image left_corner lock minus_down minus_over minus_up plus_down plus_over plus_up right_corner selected_back selected_left selected_right separator unselected_back unselected_left unselected_right)

titles=(back  left right)
titles_no_background=(config_down config_over config_up focus_down focus_over focus_up quit_down quit_over quit_up)

# Export tabs
mkdir -p ./tabs
cd ./tabs
path="tabs"
for element in ${tabs[*]}; do
	echo "Rendering $element"
	render_image $element
done
cd ..

# Export titles
mkdir -p ./title
cd ./title
path="title"
for element in ${titles[*]}; do
	echo "Rendering $element"
	render_image $element
done
for element in ${titles_no_background[*]}; do
	echo "Rendering $element"
	render_image_no_background $element
done
cd ..

echo "Finished rendering."
exit 0
