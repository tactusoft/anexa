package co.com.tactusoft.crm.view.backing;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.controller.bo.ProcessBo;

@Named
@Scope("session")
public class UploadBacking {

	@Inject
	protected ProcessBo processService;

	private UploadedFile uploadedFile;
	private UploadedFile uploadedFile2;

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public UploadedFile getUploadedFile2() {
		return uploadedFile2;
	}

	public void setUploadedFile2(UploadedFile uploadedFile2) {
		this.uploadedFile2 = uploadedFile2;
	}

	public StreamedContent getUploadedFileAsStream() {
		if (uploadedFile != null) {
			return new DefaultStreamedContent(new ByteArrayInputStream(
					uploadedFile.getContents()));
		}
		return null;
	}

	public StreamedContent getUploadedFileAsStream2() {
		if (uploadedFile2 != null) {
			return new DefaultStreamedContent(new ByteArrayInputStream(
					uploadedFile2.getContents()));
		}
		return null;
	}

	public void uploadFile(FileUploadEvent event) {
		uploadedFile = null;
		uploadedFile = event.getFile();
	}

	public void uploadFile2(FileUploadEvent event) {
		uploadedFile = null;
		uploadedFile2 = event.getFile();
	}

	public StreamedContent getBytesToStreamedContent()
			throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			Long consentId = Long.parseLong(context.getExternalContext()
					.getRequestParameterMap().get("CONSENT_ID"));
			byte[] bytes = processService.getConsentById(consentId).getConsentFile();
			return new DefaultStreamedContent(new ByteArrayInputStream(bytes));
		}
	}

}
