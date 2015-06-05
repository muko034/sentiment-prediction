#!/usr/bin/env python

from sys import argv, stderr
from optparse import OptionParser
import subprocess
import os
# CONSTANTS
LIBSVM = "../libs/libsvm-3.20/"
LIBSHORTTEXT = "../libs/libshorttext-1.1/"
DANESVM="../svm_data/"
DANELABELS="../labels/"
DANERATING="../rating/"
MODEL="../model/"
RESULT="../result/"
# [...]

def main():
    print "hello world\n"
    options()
    convert(DANELABELS+"books_training.txt","books_training.txt.svm")
    convert(DANELABELS+"books_evaluate.txt","books_evaluate.txt.svm")
    train(DANESVM+"books_evaluate.txt.svm")
    predict(DANESVM+"books_evaluate.txt.svm","books_evaluate.txt.svm.model","books_result.txt")

def options():
    usage = "usage: %prog [options] arg"
    parser = OptionParser(usage)
    parser.add_option("-f", "--file", dest="filename",
                      help="read data from FILENAME")
    parser.add_option("--libsmv", dest="libsmv", default=LIBSVM,
                      help="set libsmv path")
    parser.add_option("--libshorttext", dest="libshorttext", default=LIBSHORTTEXT,
                      help="set libshorttext path")
    parser.add_option("-v", "--verbose",
                      action="store_true", dest="verbose")
    parser.add_option("-q", "--quiet",
                      action="store_false", dest="verbose")

    (options, args) = parser.parse_args()

    #if len(args) != 1:
        #parser.error("incorrect number of arguments.\n\t\ttype \""+__file__+" -h\" for mor information")

    if options.verbose:
        print "reading %s..." % options.filename

def convert(src,dst):
	print "python " + LIBSHORTTEXT + "text2svm.py "+ src + " " + DANESVM + dst
	if not os.path.exists(DANESVM):
    		os.makedirs(DANESVM)
	if not os.path.exists(DANESVM+dst):
		subprocess.call("python " + LIBSHORTTEXT + "text2svm.py "+ src + " " + DANESVM + dst, shell=True)

def train(src):
	tempdir=os.getcwd()
	if not os.path.exists(MODEL):
    		os.makedirs(MODEL)
	os.chdir(MODEL)
	print os.getcwd();
	subprocess.call(LIBSVM + "svm-train -s 3 -p 0.1 -t 1 "+ src, shell=True)
	os.chdir(tempdir)

def predict(src,model,result):
	if not os.path.exists(RESULT):
    		os.makedirs(RESULT)
	subprocess.call(LIBSVM + "svm-predict "+ src + " " + MODEL+model + " " + RESULT+result, shell=True)

if __name__ == "__main__":
    main()

