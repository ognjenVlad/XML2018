<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:record="http://www.health_care.com/record"
                exclude-result-prefixes="xs"
                version="2.0">

    <xsl:template match="/">
    	<body>
	    	<h2>Health record</h2>
	        <table border="1">
	            <tr bgcolor="#0377fc">
	            	<th><b>Id</b></th>
					<th><b>Patient jmbg</b></th>
					<th><b>Doctor id</b></th>
	            </tr>
	            <tr bgcolor="#5ca8ab">
	            	<td><xsl:value-of select="record:record/@id"/></td>
	            	<td><xsl:value-of select="record:record/record:patient_jmbg"/></td>
	            	<td><xsl:value-of select="record:record/record:doctor_id"/></td>
	            </tr>
	        </table>
    	</body>
    </xsl:template>

</xsl:stylesheet>