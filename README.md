Processing for Max/MSP & Matlab
===============================

**WARNING:** This code dates from 2010 and is **no longer actively maintained**. I offer no support or bugfixes.

If you'd like to fork & patch: Pull requests are welcome.

License
=======
<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/InteractiveResource" property="dct:title" rel="dct:type">Processing for Max & Matlab</span> by <a xmlns:cc="http://creativecommons.org/ns#" href="http://www.madebymark.nl" property="cc:attributionName" rel="cc:attributionURL">Mark Marijnissen</a> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.<br />Based on a work at <a xmlns:dct="http://purl.org/dc/terms/" href="https://github.com/markmarijnissen/processing-for-max-and-matlab" rel="dct:source">https://github.com/markmarijnissen/processing-for-max-and-matlab</a>.

MAX/MSP:
========
Unzip to "..\Cycling '74\Max 5.0\Cycling '74\java".
Alternatively, edit the "max.java.config.txt" to point to the "core.jar" in the lib-directory
and classes in the "classes" directory.

MATLAB:
=======
Run "install.m" to add "core.jar"  to your classpath.
Or do it manually by editing the "classpath.txt" in "[matlabroot]/toolbox/local"

NOTE:
=====
There are two different "Processing.java"s.
One has MAX/MSP support, but requires the "max.jar" (in the maxmsp package),
the other doesn't have it (in the Processing package).

They only differ a few lines of code, so you might want to delete the version that you don't need 
to avoid confusion. You can also use the MAX/MSP version in Matlab if you add the "max.jar"!

MORE INFORMATION:
Read the comments in "processing.java", or try the example patch / m-file.