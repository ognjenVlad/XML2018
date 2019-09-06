<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:record="http://www.health_care.com/record" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">

    <xsl:template match="/">
    	<body>
    		<div style="display: flex; justify-content: center; flex-direction: column;">
    		<h2>Health record</h2>
	        <table border="1">
	            <tr bgcolor="#0377fc">
	            	<th>
                            <b>Id</b>
                        </th>
					<th>
                            <b>Patient jmbg</b>
                        </th>
					<th>
                            <b>Doctor id</b>
                        </th>
	            </tr>
	            <tr bgcolor="#5ca8ab">
	            	<td>
                            <xsl:value-of select="record:record/@id"/>
                        </td>
	            	<td>
                            <xsl:value-of select="record:record/record:patient_jmbg"/>
                        </td>
	            	<td>
		            	<xsl:variable name="hyperlink">
                                <xsl:value-of select="record:record/record:doctor_id"/>
                            </xsl:variable>
	                    <a href="http://localhost:8181/exist/rest/db/health_care_system/doctors/{$hyperlink}">
                                <xsl:value-of select="record:record/record:doctor_id"/>
                            </a>
	            	</td>
	            </tr>
	        </table>
    		</div>
	    	
    	</body>
    </xsl:template>

</xsl:stylesheet>