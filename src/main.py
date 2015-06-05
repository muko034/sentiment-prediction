#!/usr/bin/env python

from sys import argv, stderr
from optparse import OptionParser

# CONSTANTS
LIBSVM = "../libs/libsvm-3.20"
LIBSHORTTEXT = "../libs/libshorttext-1.1"
# [...]

def main():
    print "hello world\n"
    options()

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

    if len(args) != 1:
        parser.error("incorrect number of arguments.\n\t\ttype \""+__file__+" -h\" for mor information")

    if options.verbose:
        print "reading %s..." % options.filename

if __name__ == "__main__":
    main()


