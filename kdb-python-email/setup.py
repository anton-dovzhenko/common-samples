# -*- coding: utf-8 -*-

from setuptools import setup, find_packages


with open('README.md') as f:
    readme = f.read()

setup(
    name='python_kdb_email',
    version='0.1.0',
    description='KDB+ and pythin integration and email generation',
    long_description=readme,
    author='Anton Dovzhenko',
    url='NONE',
    #packages=find_packages(exclude=('tests', 'docs'))
)
