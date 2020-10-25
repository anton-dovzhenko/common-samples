// transferFile copies file from local to remote KDB process
// @remoteProcess - remote process details in `:host:port:login:password format
// @localFile - path to local file as handler `:filepath
// @remoteFile - path to remote file as handler `:filepath
// Example
// transferFile[`$":localhost:5556";`$":/tmp/to_copy.pdf";`$":/tmp/copied.pdf"]
transferFile: {[remoteProcess;localFile;remoteFile]
    // open connection to remote process
    h: hopen remoteProcess;
    // load local file as binary
    localFileBin: read1 localFile;
    // copy local data to remote process and save it to remote file
    h({x set y};remoteFile;localFileBin);
    // close connection to remote process
    @[hclose;h;()];
};

