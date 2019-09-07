<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:referral="http://www.health_care.com/referral" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">

    <xsl:template match="/">
    	<body>
    		<div style="display: flex; justify-content: center; flex-direction: column;">
    		<h2>Referral</h2>
    		
    		<h4>Patient info:</h4>
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
	            <tr bgcolor="#c8e3d7">
	            	<td>
                        <xsl:value-of select="referral:referral/@id"/>
                    </td>
	            	<td>
                        <xsl:value-of select="referral:referral/referral:patient_jmbg"/>
                    </td>
	            	<td>
		            	<xsl:variable name="hyperlink">
                            <xsl:value-of select="referral:referral/referral:doctor_id"/>
                        </xsl:variable>
	                    <a href="http://localhost:8181/exist/rest/db/health_care_system/doctors/{$hyperlink}">
                            <xsl:value-of select="referral:referral/referral:doctor_id"/>
                        </a>
	            	</td>
            	</tr>
    	    </table>
    	    <h4 style="margin-top: 5rem;">Referral info:</h4>
    	    <table border="1">
	            <tr bgcolor="#0377fc">
	            	<th>
                    	<b>Referral</b>
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
                      	<xsl:copy-of select="referral:referral/referral:to_doctor_id/node()"/>
                    </td>
	            	<td>
                   		<xsl:value-of select="referral:referral/referral:date"/>
                    </td>
	            	<td>
                        <xsl:value-of select="referral:referral/referral:time"/>
                     </td>
	            </tr>
	    	</table>
    		</div>
    	</body>
    </xsl:template>

</xsl:stylesheet>