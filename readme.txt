Copy the whole folder to your local, and double click SanityTestHud.jar to run.

Check your settings are correct.

To enable DEVMON automation, you need:

1. go to your jre/lib/security folder.

	similar to C:\Program Files\Java\jre1.8.0_231\lib\security

2. backup your javaws.policy

3. copy the javaws.policy (in the SHD folder) to that folder.

4. check your environment, make C:\Program Files\Java\jre1.8.0_231\bin to the top of the <path>, the java update will reset and mess up the path periodically.

5. If you launch IE failed. You may need set: 
	Internet Options->Security->check 'Enable Protected Mode' for all 4 zones.