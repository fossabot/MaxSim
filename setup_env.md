#### University of Crete
#### Department of Computer Science
#### CS-446: Managed Runtime Systems 
#### (as part of the project)
#### Authors: Ioannis Vardas (vardas@csd.uoc.gr), Antonios Psistakis (psistakis@csd.uoc.gr)
#### Date: May 16th, 2018



# Installing MaxSim on Ubuntu 14.04

## We recommend using a virtual machine to install the MaxSim tools since the compiler and dependecies of the pin tool cannot be subverted. 

1. install jdk1.7.0_25 - in this tutorial we have installed java in /opt/java. To install java add it to 
the PATH variable and also set the JAVA_HOME variable:
export PATH=$PATH:/opt/java/jdk1.7.0_25/bin 
export JAVA_HOME=/opt/java/jdk1.7.0_25/ 

2. install protobuf-2.6.1 that is difficult to find through google since it shows
new versions 3+. The version we need is in this https://github.com/google/protobuf/releases/tag/v2.6.1
Use ./configure --prefix=<any path>. We suggest not to use the default path. For our example we used 
./configure --prefix=/var/hy446_project/protobuf and also set:
export PROTOBUFPATH=/var/hy446_project/protobuf

3. Now run the scripts ./scripts/generateMaxSimInterface.sh, ./scripts/setZSimKernelParameters.sh.
You should be getting no errors at this point.

4. Remove -Werror from makefile in com.oracle.max.vm.native/platform/platform.mk (2 occurances).
Edit the error of fscanf in maxine/com.oracle.max.vm.native/tele/linux/linuxTask.c function 'log_task_stat' line 159.
Run ./scripts/buildMaxineProduct.sh

5. Run ./scripts/buildImageC1XProduct.sh - should run without problems.

6. Download Pin tool(2.14) modify pin-2.14-71313-gcc.4.4.7-linux/source/include/pin/compiler_version_check2.H to ignore compiler version
 gcc-4.4.7 is too old for our system.

7. After installing libhdf5 go to /usr/lib/x86_64-linux-gnu and create the two symbolic links 
sudo ln -s libhdf5_serial.so.10.1.0 libhdf5.so
sudo ln -s libhdf5_serial_hl.so.10.0.2 libhdf5_hl.so

8. We recommend to not set POLARSSL env var at all and disable POLARSSL from SConstruct file in zsim folder.
 
10. To set McPat framework the current script needs to be corrected. Our solution was to correct scripts and create a new environment
variable MCBIN (see below).

### set the environment variables:
export PATH=$PATH:/home/cs446/jdk1.7.0_25/bin  

export JAVA_HOME=/home/cs446/jdk1.7.0_25  

export PROTOBUFPATH=/home/cs446/protobuf  

export PINPATH=/home/cs446/pin-2.14-71313-gcc.4.4.7-linux  

export LIBCONFIGPATH=/usr/lib/x86_64-linux-gnu  

export MCPATPATH=/home/cs446/mcpat  

export MCBIN=/home/cs446/mcpat/mcpat  


11. After setting the variables the installation can be completed by running ./scripts/buildZsimProduct.sh and ./scripts/buildMaxSimProduct.sh.

12. To run the Dacapo benchmark suite a correction to the script "./scripts/runMaxSimDacapo.sh" has to be done by changing the wget command to the
following: wget --no-check-certificate "https://sourceforge.net/projects/dacapobench/files/archive/9.12-bach/dacapo-9.12-bach.jar".

13. In the git branch repository provided, additional scripts and an additional ZSim configuration are also given.
