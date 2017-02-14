import os
import smtplib

from email.mime.text import MIMEText

smtpLogin = os.environ.get('smtp.password')
smtpPassword = os.environ.get('smtp.login')

html = '<b>HTML</b>'
msg = MIMEText(html, 'html')
msg['Subject'] = 'python test'
msg['From'] = 'Noname'
msg['To'] = smtpLogin

s = smtplib.SMTP('smtp.gmail.com:587')
s.ehlo()
s.starttls()
s.ehlo()
s.login(smtpLogin, smtpPassword)
s.send_message(msg)
s.quit()


