<xsl:stylesheet xmlns:doctor="http://www.health_care.com/doctor" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:user="http://www.health_care.com/user" exclude-result-prefixes="xs" version="2.0">

    <xsl:template match="/">
    	<body>
    		<div style="display: flex; justify-content: center; flex-direction: column;">
    		<h2>Doctor info</h2>
    		
	        <table border="1">
	            <tr bgcolor="#0377fc">
					<th>
                        <b>JMBG</b>
                    </th>
					<th>
                        <b>Name</b>
                    </th>
                    <th>
                        <b>Lastname</b>
                    </th>
                    <th>
                        <b>Username</b>
                    </th>
	            </tr>
	            
	            <tr bgcolor="#c8e3d7">
	            	<td>
                        <xsl:value-of select="//*[@id]/jmbg"/>
                    </td>
	            	<td>
		            	<xsl:value-of select="//*[@id]/name"/>
	            	</td>
	            	<td>
		            	<xsl:value-of select="//*[@id]/lastname"/>
	            	</td>
	            	<td>
		            	<xsl:value-of select="//*[@id]/username"/>
	            	</td>
            	</tr>
           	</table>
    		</div>
    	</body>
    </xsl:template>

</xsl:stylesheet>