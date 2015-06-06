#!/usr/bin/env python

from sys import argv, stderr
from optparse import OptionParser
import subprocess
import os
import shutil

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
MODEL_LIBSVM=TRAIN_SVM+".model"
MODEL_LIBSHORTTEXT=TRAIN_TXT+".model"
RESULT="result.txt"
CLASSIFICATION=0
REGRESSION=1
BOOKS = 0
DVD = 1
ELECTRONICS = 2
KITCHEN = 3
DOMAIN_DIRS = [ BOOKS_DIR, DVD_DIR, ELECTRONICS_DIR, KITCHEN_DIR ]
METHODS_DIRS = [ "classification/", "regression/" ]

def main():
    print "hello world\n"
    parse_oprions()

    if options.clean:
        if os.path.exists(MODEL_DIR):
            print "removing " + MODEL_DIR
            shutil.rmtree(MODEL_DIR)
        if os.path.exists(RESULT_DIR):
            print "removing " + RESULT_DIR
            shutil.rmtree(RESULT_DIR)
        if os.path.exists(DATASVM_DIR):
            print "removing " + DATASVM_DIR
            shutil.rmtree(DATASVM_DIR)
        return

    path_svm = DATASVM_DIR + DOMAIN_DIRS[options.domain] + METHODS_DIRS[options.method]
    train_path_svm = path_svm + TRAIN_SVM
    test_path_svm = path_svm + TEST_SVM
    model_path = MODEL_DIR + DOMAIN_DIRS[options.domain] + METHODS_DIRS[options.method]
    result_path =  RESULT_DIR + DOMAIN_DIRS[options.domain] + METHODS_DIRS[options.method] + RESULT

    if options.method == CLASSIFICATION:
        path_txt = DATALABELS_DIR
        model_path += MODEL_LIBSHORTTEXT
    elif options.method == REGRESSION:
        path_txt = DATARATING_DIR
        model_path += MODEL_LIBSVM

    path_txt += DOMAIN_DIRS[options.domain]

    train_path_txt = path_txt + TRAIN_TXT
    test_path_txt = path_txt + TEST_TXT

    if options.method == CLASSIFICATION:
        train(train_path_txt, options.method)
        predict(test_path_txt, model_path, result_path, options.method)
    elif options.method == REGRESSION:
        convert(train_path_txt, train_path_svm)
        convert(test_path_txt, test_path_svm)
        train(train_path_svm, options.method)
        predict(test_path_svm, model_path, result_path, options.method)

    


    
def parse_oprions():
    global options
    usage = "usage: %prog [options] arg"
    parser = OptionParser(usage)
    parser.add_option("-f", "--file", dest="filename",
                      help="read data from FILENAME")
    parser.add_option("--libsmv", dest="libsmv", default=LIBSVM_DIR,
                      help="set libsmv path")
    parser.add_option("--libshorttext", dest="libshorttext", default=LIBSHORTTEXT_DIR,
                      help="set libshorttext path")
    parser.add_option("-m", "--method", type="int", dest="method", default=0,
                      help="set method 0-CLASSIFICATION, 1-REGRESSION")
    parser.add_option("-d", "--domain", type="int", dest="domain", default=0,
                      help="set domain 0-BOOKS, 1-DVD, 2-ELECTRONICS, 3-KITCHEN")
    parser.add_option("-c", "--clean",
                      action="store_true", dest="clean")
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
        idx = dst.rfind("/")
        dst_dir = dst[0:idx]

	if not os.path.exists(dst_dir):
    		os.makedirs(dst_dir)

	if not os.path.exists(dst):
                cmd = "python " + LIBSHORTTEXT_DIR + "text2svm.py "+ src + " " + dst
                print cmd + "\n"
		subprocess.call(cmd, shell=True)

def train(src, method):
	tempdir=os.getcwd()

	files = src.split("/")

	if method == CLASSIFICATION:
            cmd =  "python " + LIBSHORTTEXT_DIR + "text-train.py -f " + src
            dst_dir = MODEL_DIR+files[-2]+"/"+METHODS_DIRS[method]
        elif method == REGRESSION:
            cmd = LIBSVM_DIR + "svm-train -s 3 -t 2 -c 20 -g 64 -p 1 " + src
            dst_dir = MODEL_DIR+files[-3]+"/"+METHODS_DIRS[method]
        
            
	if not os.path.exists(dst_dir):
    		os.makedirs(dst_dir)
    	print "cd " + dst_dir + "\n"
	os.chdir(dst_dir)
            
	print cmd + "\n"
	subprocess.call(cmd, shell=True)
	print "cd " + tempdir + "\n"
	if os.path.exists(TRAIN_SVM):
    	    svmdir=DATASVM_DIR+files[-2]+"/"+METHODS_DIRS[CLASSIFICATION]
    	    if not os.path.exists(svmdir):
    		os.makedirs(svmdir)
    		shutil.move(TRAIN_SVM, svmdir+TRAIN_SVM)
	os.chdir(tempdir)

def predict(test_data, model, result, method):
        files = result.split("/")
    	files=files[-4:-1]
    	dst_dir="/"
    	dst_dir=dst_dir.join(files)

	if not os.path.exists(dst_dir):
    		os.makedirs(dst_dir)

    	if method == CLASSIFICATION:
            cmd =  "python " + LIBSHORTTEXT_DIR + "text-predict.py -f " + test_data + " " + model + " " + result
        elif method == REGRESSION:
            cmd = LIBSVM_DIR + "svm-predict "+ test_data + " " + model + " " + result
        
        print cmd + "\n"
	subprocess.call(cmd, shell=True)
	
	if os.path.exists(PROJECT_DIR+TEST_SVM):
    	    svmdir=DATASVM_DIR+files[-2]+"/"+METHODS_DIRS[CLASSIFICATION]
    	    if not os.path.exists(svmdir):
    		os.makedirs(svmdir)
    	    shutil.move(PROJECT_DIR+TEST_SVM, svmdir+TEST_SVM)

if __name__ == "__main__":
    main()

