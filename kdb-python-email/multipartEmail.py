import os
import smtplib
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart

from email.mime.text import MIMEText

smtpLogin=os.environ.get('smtp.password')
smtpPassword=os.environ.get('smtp.login')


msg = MIMEMultipart()
msg['Subject'] = 'python test'

html="""
<table>
<tr><td><b>HTML 1</b></td></tr>
<tr><td><img src='cid:image1'/></td></tr>
<tr><td><b font='color:red;'>HTML 2</b></td></tr>
</table>
"""
msg.attach(MIMEText(html, 'html'))
attachment = MIMEText('id,value,\n1,"aaa",\n2,"bbb"', _subtype='octet-stream')
attachment.add_header('Content-Disposition', 'attachment', filename="rawData.csv")
msg.attach(attachment)

fp = open('pandasPlot.png', 'rb')
image = MIMEImage(fp.read())
fp.close()
image.add_header('Content-ID', '<image1>')
msg.attach(image)

composed = msg.as_string()

s = smtplib.SMTP('smtp.gmail.com:587')
s.ehlo()
s.starttls()
s.ehlo()
s.login(smtpLogin, smtpPassword)
s.sendmail('Anton', smtpLogin, composed)
s.quit()
