# jme3-vlcj
example of vlcj usage in jmonkeyengine3 (Based on: http://capricasoftware.co.uk/#/projects/vlcj/tutorial/direct-rendering)

How to make it work:

Detailed post: http://www.softwarepioneering.com/2016/12/playing-video-in-jmonkeyengine-3-using.html

Brief description:

1. Clone https://github.com/caprica/vlcj

2. Build a distribution (This is not entirely straight-forward for Netbeans users. It's a Maven project)

3. Link the distribution in your project (Hint: It ends up in target/vlcj-version-SNAPSHOT-dist)

4. Run TestVlcj and pass a filename as an argument (or replace "mediaFile" with something suitable)

License:
This is GPL v3.0 to heed to the original vlcj project. Anyone with a commercial license to use vlcj is free to use this project under the same terms.


