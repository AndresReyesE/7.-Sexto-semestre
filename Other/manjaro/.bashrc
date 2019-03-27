#
# ~/.bashrc
#

[[ $- != *i* ]] && return

colors() {
	local fgc bgc vals seq0

	printf "Color escapes are %s\n" '\e[${value};...;${value}m'
	printf "Values 30..37 are \e[33mforeground colors\e[m\n"
	printf "Values 40..47 are \e[43mbackground colors\e[m\n"
	printf "Value  1 gives a  \e[1mbold-faced look\e[m\n\n"

	# foreground colors
	for fgc in {30..37}; do
		# background colors
		for bgc in {40..47}; do
			fgc=${fgc#37} # white
			bgc=${bgc#40} # black

			vals="${fgc:+$fgc;}${bgc}"
			vals=${vals%%;}

			seq0="${vals:+\e[${vals}m}"
			printf "  %-9s" "${seq0:-(default)}"
			printf " ${seq0}TEXT\e[m"
			printf " \e[${vals:+${vals+$vals;}}1mBOLD\e[m"
		done
		echo; echo
	done
}

[ -r /usr/share/bash-completion/bash_completion ] && . /usr/share/bash-completion/bash_completion

# Change the window title of X terminals
case ${TERM} in
	xterm*|rxvt*|Eterm*|aterm|kterm|gnome*|interix|konsole*)
		PROMPT_COMMAND='echo -ne "\033]0;${USER}@${HOSTNAME%%.*}:${PWD/#$HOME/\~}\007"'
		;;
	screen*)
		PROMPT_COMMAND='echo -ne "\033_${USER}@${HOSTNAME%%.*}:${PWD/#$HOME/\~}\033\\"'
		;;
esac

use_color=true

# Set colorful PS1 only on colorful terminals.
# dircolors --print-database uses its own built-in database
# instead of using /etc/DIR_COLORS.  Try to use the external file
# first to take advantage of user additions.  Use internal bash
# globbing instead of external grep binary.
safe_term=${TERM//[^[:alnum:]]/?}   # sanitize TERM
match_lhs=""
[[ -f ~/.dir_colors   ]] && match_lhs="${match_lhs}$(<~/.dir_colors)"
[[ -f /etc/DIR_COLORS ]] && match_lhs="${match_lhs}$(</etc/DIR_COLORS)"
[[ -z ${match_lhs}    ]] \
	&& type -P dircolors >/dev/null \
	&& match_lhs=$(dircolors --print-database)
[[ $'\n'${match_lhs} == *$'\n'"TERM "${safe_term}* ]] && use_color=true

if ${use_color} ; then
	# Enable colors for ls, etc.  Prefer ~/.dir_colors #64489
	if type -P dircolors >/dev/null ; then
		if [[ -f ~/.dir_colors ]] ; then
			eval $(dircolors -b ~/.dir_colors)
		elif [[ -f /etc/DIR_COLORS ]] ; then
			eval $(dircolors -b /etc/DIR_COLORS)
		fi
	fi

	if [[ ${EUID} == 0 ]] ; then
		PS1="\[$(tput setaf 1)\]«\u\\[$(tput sgr0)\]\[$(tput bold)\] \W\\[$(tput sgr0)\]\[$(tput setaf 1)\]»\$ \[$(tput sgr0)\]"
	else
		PS1="\[$(tput setaf 6)\]«\u\\[$(tput sgr0)\]\[$(tput bold)\] \W\\[$(tput sgr0)\]\[$(tput setaf 6)\]»φ \[$(tput sgr0)\]"
	fi

	alias ls='ls --color=auto'
	alias grep='grep --colour=auto'
	alias egrep='egrep --colour=auto'
	alias fgrep='fgrep --colour=auto'
else
	if [[ ${EUID} == 0 ]] ; then
		# show root@ when we don't have colors
		PS1="«\u \W»φ "
	else
		PS1="«\u \W»\$ "
	fi
fi

unset use_color safe_term match_lhs sh

alias cp="cp -i"                          # confirm before overwriting something
alias df='df -h'                          # human-readable sizes
alias free='free -m'                      # show sizes in MB
alias np='nano -w PKGBUILD'
alias more=less

xhost +local:root > /dev/null 2>&1

complete -cf sudo

# Bash won't get SIGWINCH if another process is in the foreground.
# Enable checkwinsize so that bash will check the terminal size when
# it regains control.  #65623
# http://cnswww.cns.cwru.edu/~chet/bash/FAQ (E11)
shopt -s checkwinsize

shopt -s expand_aliases

# export QT_SELECT=4

# Enable history appending instead of overwriting.  #139609
shopt -s histappend

#
# # ex - archive extractor
# # usage: ex <file>
ex ()
{
  if [ -f $1 ] ; then
    case $1 in
      *.tar.bz2)   tar xjf $1   ;;
      *.tar.gz)    tar xzf $1   ;;
      *.bz2)       bunzip2 $1   ;;
      *.rar)       unrar x $1     ;;
      *.gz)        gunzip $1    ;;
      *.tar)       tar xf $1    ;;
      *.tbz2)      tar xjf $1   ;;
      *.tgz)       tar xzf $1   ;;
      *.zip)       unzip $1     ;;
      *.Z)         uncompress $1;;
      *.7z)        7z x $1      ;;
      *)           echo "'$1' cannot be extracted via ex()" ;;
    esac
  else
    echo "'$1' is not a valid file"
  fi
}

# better yaourt colors
export YAOURT_COLORS="nb=1:pkg=1:ver=1;32:lver=1;45:installed=1;42:grp=1;34:od=1;41;5:votes=1;44:dsc=0:other=1;35"

export PATH="/home/reyes/Applications/anaconda3/bin:$PATH"

export PATH="/home/reyes/Applications/mongodb-2.6/bin:$PATH"

alias ..='cd ..'

checkdir () {
    if [ -d "$1" ]; then
	echo 0
    else
	echo 1
    fi
}

checkfile () {
    if [ -s "$1" ]; then
        echo 0
    else
        echo 1
    fi
}

f () {
    exists=$(checkdir $1)
    if [ exists=0 ]; then
	touch $1/file 
    else
	touch ./file1
    fi
}

contains () {
    if [[ "$1" != *"$2"* ]]; then
        echo 0
    else
        echo 1
    fi
}

rcsync () {

#------------------------------------------#
    if [[ "$1" != *":"* ]]; then
        if [ -s "$1" ]; then
            if [ ! -d "$1" ]; then
                echo "$1 is not a directory, cancelling operation"
                return 2
            fi
        fi
    fi
    
    if [[ "$2" != *":"* ]]; then
        if [ -s "$2" ]; then
            if [ ! -d "$2" ]; then
                echo "$2 is not a directory, cancelling operation"
                return 2
            fi
        fi
    fi
#------------------------------------------#    
    
    
#------------------------------------------#    
    if [ "$1" != *":"* -a "$2" != *":"* ]; then
        if [ ! -d "$1" -a ! -d "$2" ]; then
            echo "None directory exists, cancelling operation"
            return 1
        fi
    fi
#------------------------------------------#    
    
    
    
#------------------------------------------#    
    if [[ "$1" != *":"* ]]; then
        if [ -d "$1" ]; then
            exists1=0
            echo ""
            echo "###### Backing up |$1| in |"$1"/../BackUp|... ######" 
            rclone sync "$1" "$1""/../BackUp" -P
            printf "\n###### Back up complete! ######\n"
        else
            exists1=1
            printf "\n$1 doesn't exist. Creating...\n"
            mkdir "$1"
            printf "\n $1 created\n"
        fi
    else
        exists1=0
        echo ""
        echo "###### Backing up |$1| in |"$1"/BackUp|... ######" 
        rclone sync "$1" "$1""/BackUp" -P
        printf "\n###### Back up complete! ######\n"
    fi
#------------------------------------------#    

#------------------------------------------#    
    if [[ "$2" != *":"* ]]; then
        if [ -d "$2" ]; then
            exists2=0
            echo ""
            echo "###### Backing up |$2| in |"$2"/../BackUp|... ######" 
            rclone sync "$2" "$2""/../BackUp" -P
            printf "\n###### Back up complete! ######\n"
        else
            exists2=1
            printf "\n$2 doesn't exist. Creating...\n"
            mkdir "$2"
            printf "\n$2 created\n"
        fi
    else
        exists2=0
        echo ""
        echo "###### Backing up |$2| in |"$2"/BackUp|... ######" 
        rclone sync "$2" "$2""/BackUp" -P
        printf "\n###### Back up complete! ######\n"
    fi
#------------------------------------------#    

    
#------------------------------------------#    
    if [ $exists1 -eq 0 ]; then
        printf "\n###### Copying new files from |$1| to |$2|... ######\n" 
        rclone copy "$1" "$2" --update -P
    else
        printf "\n $1 empty, omitting...\n"
    fi
    
    if [ $exists2 -eq 0 ]; then
        printf "\n###### Copying new files from |$2| to |$1|... ######\n" 
        rclone copy "$2" "$1" --update -P
    else
        printf "\n $2 empty, omitting...\n"
    fi
#------------------------------------------#    
}


alias starwars='telnet towel.blinkenlights.nl'
alias l='ls -l'
alias 7='cd /home/reyes/7.-Sexto-semestre'
alias kde='kdeconnect-cli'
alias kdh='kdeconnect-handler'

tt () {
    echo "/$1/ /$2/ /"$@"/"
    mkdir $1
#    mkdir $1"/../partial$1"
#    mkdir "$1/../inclusive$1"
#    mkdir $1"/../exclusive"$1
}

gitcache () {
    git config credential.helper store 
}



