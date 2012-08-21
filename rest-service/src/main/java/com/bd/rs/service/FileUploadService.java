package com.bd.rs.service;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/UploadFile")
public class FileUploadService {
	private static Logger logger = LoggerFactory.getLogger(FileUploadService.class);

	@POST
	// public String upload(@Context HttpServletRequest request, @PathParam("myfile") String fileName) throws Exception {
	public String upload(@Context HttpServletRequest request) throws Exception {

		String response = "none";

		if (ServletFileUpload.isMultipartContent(request)) {

			response = "got file in request";

			// Create a factory for disk-based file items
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();

			
			 String path = request.getRealPath("") + File.separatorChar + "publishFiles" + File.separatorChar;
//			String path = request.getSession().getServletContext() + File.separatorChar + "publishFiles" + File.separatorChar;

			// File f = new File(path + "myfile.txt");
			// File tmpDir = new File("c:\\tmp");

			File destinationDir = new File(path);

			// Set the size threshold, above which content will be stored on disk.
			// fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB

			// Set the temporary directory to store the uploaded files of size above threshold.
			// fileItemFactory.setRepository(tmpDir);

			// Create a new file upload handler
			ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

			try {
				/*
				 * Parse the request
				 */
				List items = uploadHandler.parseRequest(request);
				Iterator itr = items.iterator();

				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					/*
					 * Handle Form Fields.
					 */
					if (item.isFormField()) {
						response += "<BR>" + "Field Name = " + item.getFieldName() + ", Value = " + item.getString();
					} else {
						// Handle Uploaded files.
						response += "<BR>" + "File Field Name = " + item.getFieldName() + ", File Name = " + item.getName() + ", Content type = "
								+ item.getContentType() + ", File Size = " + item.getSize();
						/*
						 * Write file to the ultimate location.
						 */
						File file = new File(destinationDir, item.getName());
						item.write(file);
					}
				}
			} catch (FileUploadException ex) {
				response += "Error encountered while parsing the request " + ex;
			} catch (Exception ex) {
				response += "Error encountered while uploading file " + ex;
			}
		}

		return response;

	}
}
