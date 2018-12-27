
Folder Structure

<basedir>			-- Base folder will contain below folders structure
<basedir>/json		-- The folder to hold in & out json folder
<basedir>/json/in	-- The folder to hold input json configurations
<basedir>/json/out	-- The folder to hold output json to be send to control tower
<basedir>/logs		-- The folder for java and shell script logs
<basrdir>/xml		-- The folder to hold the xml file needed for soap services
<basedir>/bin		-- The folder to hold the Jar file and wrapper shell scripts
<basedir>/cfg		-- The folder to hold the configuration file for wrapper scripts



Run Properties for Jar 
-Dlogger.loglevel=<INFO/DEBUG/ERROR>
-Dbasedir=<base-directory> 
-Dinjsonfile=<input-json-file-name>
-Doutjsonfile=<output-json-file-name>


		
