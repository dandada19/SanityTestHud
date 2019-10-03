Copy the whole folder to your local, and double click SanityTestHud.jar to run.

Check your settings are correct.

To enable DEVMON/ENROLL automation, you need:

1. go to your jre/lib/security folder.

	similar to C:\Program Files\Java\jre1.8.0_211\lib\security

2. backup your javaws.policy

3. copy the javaws.policy to that folder.

4. check your environment, make C:\Program Files\Java\jre1.8.0_221\bin to the top of the <path>, the system update will reset and mess up the path periodically.