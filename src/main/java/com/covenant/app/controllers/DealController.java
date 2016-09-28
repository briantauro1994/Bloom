package com.covenant.app.controllers;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.InputSource;

import com.covenant.app.dto.DealDto;
import com.covenant.app.model.Deal;
import com.covenant.app.services.DealService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.api.storage.ObjectStorageService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.openstack.OSFactory;


@Controller
@RequestMapping("/v1/deals")
public class DealController {
	private static final String USERNAME = "admin_5bcde9b5fb6ffed725c619b9c663bc4807fac048";
	private static final String PASSWORD = "F4b~rl.=MqsU1nB.";
	private static final String DOMAIN_ID = "f7043eb3753149ccb43612571178741e";
	private static final String PROJECT_ID = "71f16b3a93b946d7ba06fd148dc6303b";

	private ObjectStorageService authenticateAndGetObjectStorageService() throws ParseException, net.minidev.json.parser.ParseException {
//		String OBJECT_STORAGE_AUTH_URL = "https://identity.open.softlayer.com";
//
//		Identifier domainIdentifier = Identifier.byName(DOMAIN_ID);
//
//		System.out.println("Authenticating...");
//		Object obj = parser.parse(envServices);
		 String envApp = System.getenv("VCAP_APPLICATION");
		 String envServices = System.getenv("VCAP_SERVICES");
		 JSONParser parser = new JSONParser();
		 Object obj = parser.parse(envServices);
		 JSONObject jsonObject = (JSONObject) obj;
		 JSONArray vcapArray = (JSONArray) jsonObject.get("Object-Storage");
		 JSONObject vcap = (JSONObject) vcapArray.get(0);
		 JSONObject credentials = (JSONObject) vcap.get("credentials");
		 String userId = credentials.get("userId").toString();
		 String password = credentials.get("password").toString();
		 String auth_url = credentials.get("auth_url").toString() + "/v3";
		 String domain = credentials.get("domainName").toString();
		 String project = credentials.get("project").toString();
		 Identifier domainIdent = Identifier.byName(domain);
		 Identifier projectIdent = Identifier.byName(project);
		 OSClient os = OSFactory.builderV3()
				 .endpoint(auth_url)
				 .credentials(userId, password)
				 .scopeToProject(projectIdent, domainIdent)
				 .authenticate();

		System.out.println("Authenticated successfully!");

		ObjectStorageService objectStorage = os.objectStorage();

		return objectStorage;
	}

	/**
	 * @throws ParseException 
	 * @throws net.minidev.json.parser.ParseException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected InputStream doGet(MultipartHttpServletRequest request) throws ServletException, IOException, ParseException, net.minidev.json.parser.ParseException {
		ObjectStorageService objectStorage = authenticateAndGetObjectStorageService();

		System.out.println("Retrieving file from ObjectStorage...");
		String containerName = request.getParameter("container");
		 String fileName = request.getFileNames().next();
			MultipartFile file =request.getFile(fileName);
logger.info("file name"+fileName+" ofilename : "+file.getOriginalFilename());
		if (containerName == null || fileName == null) { // No file was
															// specified to be
															// found, or
															// container name is
															// missing
			logger.info("File null"+HttpServletResponse.SC_NOT_FOUND);
			System.out.println("Container name or file name was not specified.");
			return null;
		}

		SwiftObject pictureObj = objectStorage.objects().get(containerName, file.getOriginalFilename());

		if (pictureObj == null) { // The specified file was not found
			logger.info("nuul file not found"+HttpServletResponse.SC_NOT_FOUND);
			System.out.println("File not found.");
			return null;
		}
		else{
		String mimeType = pictureObj.getMimeType();

		DLPayload payload = pictureObj.download();

		InputStream in = payload.getInputStream();
		System.out.println("Successfully retrieved file from ObjectStorage!");
		return in;
		}
		
	
	}

	protected void doPost(MultipartHttpServletRequest request ) throws ServletException, IOException, ParseException, net.minidev.json.parser.ParseException {
		ObjectStorageService objectStorage = authenticateAndGetObjectStorageService();

		System.out.println("Storing file in ObjectStorage...");

	 String containerName = "test";

		
		 String fileName = request.getFileNames().next();
		 System.out.println("fileName..."+fileName);
			if (containerName == null || fileName == null) { // No file was
															// specified to be
															// found, or
															// container name is
															// missing
			logger.info("File not found cause null "+HttpServletResponse.SC_NOT_FOUND);
			System.out.println("File not found.");
			return;
		}
			MultipartFile file =request.getFile(fileName);
			byte[] imgBytes = file.getBytes();
			logger.info("Length"+imgBytes.length+" fileName :"+fileName+" extension : "+file.getOriginalFilename()+" Actual extendion from file : "+FilenameUtils.getExtension(file.getOriginalFilename()));
			System.out.println("Length"+imgBytes.length);
	//	final InputStream fileStream=  new FileInputStream(imgBytes);
		ByteArrayInputStream bs;
		bs = new ByteArrayInputStream(imgBytes);

		Payload<	ByteArrayInputStream> payload = new PayloadClass(bs);

		objectStorage.objects().put(containerName, file.getOriginalFilename(), payload);

		System.out.println("Successfully stored file in ObjectStorage!");
	}

	private class PayloadClass implements Payload<ByteArrayInputStream> {
		private ByteArrayInputStream stream = null;

		public PayloadClass(ByteArrayInputStream stream) {
			this.stream = stream;
		}

		@Override
		public void close() throws IOException {
			stream.close();
		}

		@Override
		public ByteArrayInputStream open() {
			return stream;
		}

		@Override
		public void closeQuietly() {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}

		@Override
		public ByteArrayInputStream getRaw() {
			return stream;
		}

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException, net.minidev.json.parser.ParseException {
		ObjectStorageService objectStorage = authenticateAndGetObjectStorageService();

		System.out.println("Deleting file from ObjectStorage...");

		String containerName = request.getParameter("container");

		String fileName = request.getParameter("file");

		if (containerName == null || fileName == null) { // No file was
															// specified to be
															// found, or
															// container name is
															// missing
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			System.out.println("File not found.");
			return;
		}

		ActionResponse deleteResponse = objectStorage.objects().delete(containerName, fileName);

		if (!deleteResponse.isSuccess()) {
			response.sendError(deleteResponse.getCode());
			System.out.println("Delete failed: " + deleteResponse.getFault());
			return;
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
		}

		System.out.println("Successfully deleted file from ObjectStorage!");
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public void findAllDeals(MultipartHttpServletRequest request,HttpServletResponse response) {
logger.info("Welcome");
		try {
			doPost(request);
			InputStream is= doGet(request);
			 String fileName = request.getFileNames().next();
			 MultipartFile file =request.getFile(fileName);
			logger.info(is);
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			logger.info("File Name "+file.getOriginalFilename());
			extension=extension.toUpperCase();
			logger.info("Extension: " + extension);
			if(extension.equals("PNG")){
				response.setContentType("image/png");
				logger.info("png");	
			}
		if(extension.equals("JPG")){
				response.setContentType("image/jpg");
				logger.info("jpg");
		}
			if(extension.equals("JPEG"))
			{
				response.setContentType("image/jpeg");
				logger.info("jpeg");
			}
				response.setHeader("Content-Disposition", "filename=" + file.getOriginalFilename());
			int c;
			while ((c = is.read()) != -1) {
				response.getWriter().write(c);
			}
			if (is != null)
				is.close();
			response.getWriter().close();
		
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (net.minidev.json.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		// return new DealDto().getAllDto(dealList);
		
try {
	response.getWriter().println("Hi");
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}



	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value="/save",method = RequestMethod.GET)
	public void saveDeal() {

		Deal deal = new Deal();
		deal.setTitle("hey");
		deal.setStatus("hi");
		dealService.saveDeal(deal);
		logger.info("New Deal is created");

	}
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value="/all",method = RequestMethod.GET)
	public List<DealDto> getall() {

		
		
		return new DealDto().getAllDto(dealService.findAllDeals());

	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> errorHandler(Exception exc) {
		logger.error(exc.getMessage(), exc);
		return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@Autowired
	private DealService dealService;

	private static final Logger logger = Logger.getLogger(DealController.class);
}
