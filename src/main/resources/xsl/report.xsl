<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:report="http://www.health_care.com/report" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">

    <xsl:template match="/">
        <html>
    	<body>
    		<div style="display: flex; justify-content: center; flex-direction: column;">
    		<h2>Medical report</h2>
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
					<th>
	                      <b>Opinion</b>
	                  </th>
					<th style="width: 10%;">
                        <b>Date</b>
                    </th>
					<th style="width: 10%;">
                        <b>Time</b>
                    </th>
	            </tr>
	            <tr bgcolor="#c8e3d7">
	            	<td>
                        <xsl:value-of select="report:report/@id"/>
                    </td>
	            	<td>
	            		<xsl:variable name="hyperlink">
                                    <xsl:value-of select="report:report/report:patient_jmbg"/>
                                </xsl:variable>
	                    <a href="http://localhost:8181/exist/rest/db/health_care_system/pacients/{$hyperlink}">
                           <xsl:value-of select="report:report/report:patient_jmbg"/>
                       </a>
                   </td>
	            	<td>
		            	<xsl:variable name="hyperlink">
                                    <xsl:value-of select="report:report/report:doctor_id"/>
                                </xsl:variable>
	                    <a href="http://localhost:8181/exist/rest/db/health_care_system/doctors/{$hyperlink}">
                                    <xsl:value-of select="report:report/report:doctor_id"/>
                                </a>
	            	</td>
	            	<td>
                                <xsl:copy-of select="report:report/report:opinion/node()"/>
                            </td>
	            	<td>
                                <xsl:value-of select="report:report/report:date"/>
                            </td>
	            	<td>
                                <xsl:value-of select="report:report/report:time"/>
                            </td>
	            </tr>
	           
	        </table>
    		</div>
	    	
    	</body>
    	</html>
    </xsl:template>

</xsl:stylesheet>