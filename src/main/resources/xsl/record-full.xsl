<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:record="http://www.health_care.com/record" 
 xmlns:xs="http://www.w3.org/2001/XMLSchema" 
 exclude-result-prefixes="xs" 
 xmlns:referral="http://www.health_care.com/referral"
	xmlns:prescription="http://www.health_care.com/prescription"
	xmlns:report="http://www.health_care.com/report"
    version="2.0">

    <xsl:template match="/">
    	<body>
    		<div style="display: flex; justify-content: center; flex-direction: column;">
    		<h2>Health record</h2>
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
                        <b>LBO</b>
                    </th>
	            </tr>
	            <tr bgcolor="#c8e3d7">
	            	<td>
                        <xsl:value-of select="record:record-full/record:patient_jmbg"/>
                    </td>
	            	<td>
                        <xsl:value-of select="record:record-full/record:patient_name"/>
	            	</td>
	            	<td>
                        <xsl:value-of select="record:record-full/record:patient_lastname"/>
	            	</td>
	            	<td>
                        <xsl:value-of select="record:record-full/record:patient_lbo"/>
	            	</td>
            	</tr>
    	    </table>
    	    
    	    <h4 style="margin-top: 5rem;" >Reports</h4>
    	    <table style="width: 700px;" border="1">
    	        <tr bgcolor="#0377fc">
    	            <th>
                        <b>Opinion</b>
                    </th>
                    <th>
                        <b>Date</b>
                    </th>
                    <th>
                        <b>Time</b>
                    </th>
                    <th>
                        <b>Doctor</b>
                    </th>
    	        </tr>
    	        <xsl:for-each select="record:record-full/record:reports">
	    	        <tr bgcolor="#c8e3d7">
		            	<td>
                        	<xsl:copy-of  select="report:opinion/node()"/>
		            	</td>
		            	<td>
                        	<xsl:value-of  select="report:date"/>
		            	</td>
		            	<td>
                        	<xsl:value-of  select="report:time"/>
		            	</td>
		            	<td>
                        	<xsl:value-of  select="report:doctor_id"/>
		            	</td>
		            </tr>
				</xsl:for-each>
    	        
	        </table>
	        <h4 style="margin-top: 5rem;">Prescriptions</h4>
	         <table style="width: 700px" border="1">
    	        <tr bgcolor="#0377fc">
                    <th>
                        <b>Drug</b>
                    </th>
                    <th>
                        <b>Date</b>
                    </th>
                    <th>
                        <b>Time</b>
                    </th>
                    <th>
                        <b>Doctor</b>
                    </th>
    	        </tr>
           		<xsl:for-each select="record:record-full/record:prescriptions">
	    	        <tr bgcolor="#c8e3d7">
		            	<td>
                        	<xsl:copy-of  select="prescription:drug/node()"/>
		            	</td>
		            	<td>
                        	<xsl:value-of  select="prescription:date"/>
		            	</td>
		            	<td>
                        	<xsl:value-of  select="prescription:time"/>
		            	</td>
		            	<td>
                        	<xsl:value-of  select="prescription:doctor_id"/>
		            	</td>
		            </tr>
				</xsl:for-each>
	        </table>
	        <h4 style="margin-top: 5rem;">Referrals</h4>
	         <table style="width: 700px" border="1">
    	        <tr bgcolor="#0377fc">
                    <th>
                        <b>Referral</b>
                    </th>
                    <th>
                        <b>Date</b>
                    </th>
                    <th>
                        <b>Time</b>
                    </th>
                    <th>
                        <b>Doctor</b>
                    </th>
    	        </tr>
    	       <xsl:for-each select="record:record-full/record:referrals">
	    	        <tr bgcolor="#c8e3d7">
		            	<td>
                        	<xsl:copy-of  select="referral:to_doctor_id/node()"/>
		            	</td>
		            	<td>
                        	<xsl:value-of  select="referral:date"/>
		            	</td>
		            	<td>
                        	<xsl:value-of  select="referral:time"/>
		            	</td>
		            	<td>
                        	<xsl:value-of  select="referral:doctor_id"/>
		            	</td>
		            </tr>
				</xsl:for-each>
	        </table>
    		</div>
	    	
    	</body>
    </xsl:template>

</xsl:stylesheet>