#!/usr/bin/env python

from sys import argv, stderr
from optparse import OptionParser
import subprocess
import os

print "kurwa"

os.chdir("..")
# CONSTANTS DEFINITIONS
PROJECT_DIR=os.getcwd()+"/"
LIBSVM_DIR = PROJECT_DIR+"libs/libsvm-3.20/"
LIBSHORTTEXT_DIR = PROJECT_DIR+"libs/libshorttext-1.1/"
DATA_DIR=PROJECT_DIR+"data/"
DATASVM_DIR=DATA_DIR+"svm_data/"
DATALABELS_DIR=DATA_DIR+"labels/"
DATARATING_DIR=DATA_DIR+"rating/"
MODEL_DIR=PROJECT_DIR+"model/"
RESULT_DIR=PROJECT_DIR+"result/"
BOOKS_DIR="books/"
DVD_DIR="dvd/"
ELECTRONICS_DIR="electronics/"
KITCHEN_DIR="kitchen/"
TRAIN_TXT="train.txt"
TEST_TXT="test.txt"
TRAIN_SVM="train.txt.svm"
TEST_SVM="test.txt.svm"
C_SVC=0
E_SVR=3
C_SVC_DIR="c-svc/"
E_SVR_DIR="e-svr/"


def main():
    print "hello world\n"
    options()
    convert(DATALABELS_DIR+BOOKS_DIR+TRAIN_TXT, DATASVM_DIR+BOOKS_DIR+TRAIN_SVM)
    convert(DATALABELS_DIR+BOOKS_DIR+TEST_TXT, DATASVM_DIR+BOOKS_DIR+TEST_SVM)
    train(DATASVM_DIR+BOOKS_DIR+TRAIN_SVM, C_SVC)
    predict(DATASVM_DIR+BOOKS_DIR+TEST_SVM, MODEL_DIR+BOOKS_DIR+C_SVC_DIR+TRAIN_SVM+".model", RESULT_DIR+BOOKS_DIR+C_SVC_DIR+"result.txt")

def options():
    usage = "usage: %prog [options] arg"
    parser = OptionParser(usage)
    parser.add_option("-f", "--file", dest="filename",
                      help="read data from FILENAME")
    parser.add_option("--libsmv", dest="libsmv", default=LIBSVM_DIR,
                      help="set libsmv path")
    parser.add_option("--libshorttext", dest="libshorttext", default=LIBSHORTTEXT_DIR,
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

def convert(src, dst):
	if not os.path.exists(DATASVM_DIR):
    		os.makedirs(DATASVM_DIR)
    		
    	files = dst.split("/")
    	files=files[-4:-1]
    	dst_dir="/"
    	dst_dir=dst_dir.join(files)

    	if not os.path.exists(dst_dir):
    		os.makedirs(dst_dir)

	if not os.path.exists(dst):
		subprocess.call("python " + LIBSHORTTEXT_DIR + "text2svm.py "+ src + " " + dst, shell=True)

def train(src, method):
	tempdir=os.getcwd()
	files = src.split("/")
    	dst_dir = MODEL_DIR+files[-2]
    	
    	if method == C_SVC:
            dst_dir = dst_dir+"/"+C_SVC_DIR
    	elif method == E_SVR:
            dst_dir = dst_dir+"/"+E_SVR_DIR
            
	if not os.path.exists(dst_dir):
    		os.makedirs(dst_dir)
	os.chdir(dst_dir)
	cmd = LIBSVM_DIR + "svm-train -s "+str(method)+" -p 0.1 -t 1 " + src
	print cmd
	subprocess.call(cmd, shell=True)
	os.chdir(tempdir)

def predict(test_data, model, result):
        files = result.split("/")
    	files=files[-4:-1]
    	dst_dir="/"
    	dst_dir=dst_dir.join(files)
    	
	if not os.path.exists(dst_dir):
    		os.makedirs(dst_dir)
        cmd = LIBSVM_DIR + "svm-predict "+ test_data + " " + model + " " + result
        print cmd
	subprocess.call(cmd, shell=True)

if __name__ == "__main__":
    main()

