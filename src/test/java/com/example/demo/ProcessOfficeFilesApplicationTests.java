package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.demo.controller.ProcessController;
import com.example.demo.model.FileRequest;
import com.example.demo.model.jasper.ContratoFakeRegisterRequest;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
class ProcessOfficeFilesApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private ProcessController controller;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		log.info("ProcessOfficeFilesApplicationTests.contextLoads");
		assertThat(controller).isNotNull();
	}

	@Test
	void controllerTest() throws Exception {
		log.info("ProcessOfficeFilesApplicationTests.controllerTest");
		String id = "67f4a66f-6d89-41bb-b639-372a078acb43";
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/api-files/getFile",
				FileRequest.builder().idFile(id).build(), String.class))
						.contains("67f4a66f-6d89-41bb-b639-372a078acb43");
	}

	@Test
	void controllerTestJasper() throws Exception {
		log.info("ProcessOfficeFilesApplicationTests.controllerTestJasper");
		String json = "{\"idTemplate\":\"323232323\",\"datosPrincipales\":{\"datosEmpleador\":{\"razonSocial\":\"SUN INVERSIONES S.A.C\",\"nroPartida\":\"00.15\",\"oficinaRegistral\":\"OFICINA LIMA\",\"ruc\":\"20486229684\",\"domicilioEmpleador\":\"AV. NICOLAS DE PIEROLA NRO. 392 INT. 2DO CERCADO DE LIMA\",\"departamentoEmpleador\":\"Lima\",\"provinciaEmpleador\":\"Lima\",\"distritoEmpleador\":\"Breña\",\"nombreRepresentante\":\"WILMAN\",\"apellidoRepresentante\":\"SALVADOR URIOL\",\"tipoDocumentoEmpleador\":\"DNI\",\"nroDocumentoEmpleador\":\"05876985\"},\"datosTrabajador\":{\"nombresTrabajador\":\"JOSE\",\"apellidosTrabajador\":\"CASTILLO CHALQUE\",\"nacionalidadTrabajador\":\"PERUANA\",\"tipoDocumentoTrabajador\":\"DNI\",\"nroDocumentoTrabajador\":\"75624412\",\"domicilioTrabajador\":\"JR RIO JORDAN MZ LOTE 1 MONTE DE SION\",\"departamentoTrabajador\":\"Lima\",\"provinciaTrabajador\":\"Lima\",\"distritoTrabajador\":\"San Juan de Lurigancho\"}},\"clausulas\":{\"rubro\":\"Entretenimiento\",\"desempeño\":\"Desarrollador Jr.\",\"experiencia\":\"3 años\",\"cargo\":\"Soporte de Sistemas\",\"actividad\":\"Sistemas\",\"laboresAsignadas\":\"Dar soporte al area de oficina y sala\",\"areaLaboral\":\"Sistemas\",\"tipoContrato\":\"Indefinido\",\"fechaInicio\":\"2020-05-01\",\"fechaHasta\":\"2020-08-01\",\"esIndefinido\":true,\"sueldo\":2300,\"moneda\":\"S\",\"laboresAsignadas2\":\"Otras labores asignadas u.u\"}}";
		String base64 = "data:application/pdf;base64,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Y2EvRW5jb2RpbmcvV2luQW5zaUVuY29kaW5nPj4KZW5kb2JqCjQgMCBvYmoKPDwvS2lkc1sxIDAgUl0vVHlwZS9QYWdlcy9Db3VudCAxL0lUWFQoMi4xLjcpPj4KZW5kb2JqCjYgMCBvYmoKPDwvTmFtZXNbKEpSX1BBR0VfQU5DSE9SXzBfMSkgNSAwIFJdPj4KZW5kb2JqCjcgMCBvYmoKPDwvRGVzdHMgNiAwIFI+PgplbmRvYmoKOCAwIG9iago8PC9OYW1lcyA3IDAgUi9UeXBlL0NhdGFsb2cvUGFnZXMgNCAwIFIvVmlld2VyUHJlZmVyZW5jZXM8PC9QcmludFNjYWxpbmcvQXBwRGVmYXVsdD4+Pj4KZW5kb2JqCjkgMCBvYmoKPDwvTW9kRGF0ZShEOjIwMjIwMzE1MjIxNDIxLTA1JzAwJykvQ3JlYXRvcihKYXNwZXJSZXBvcnRzIExpYnJhcnkgdmVyc2lvbiA2LjE5LjAtNjQ2YzY4OTMxY2ViZjFhNThiYzY1YzQzNTlkMWYwY2EyMjNjNWU5NCkvQ3JlYXRpb25EYXRlKEQ6MjAyMjAzMTUyMjE0MjEtMDUnMDAnKS9Qcm9kdWNlcihpVGV4dCAyLjEuNyBieSAxVDNYVCk+PgplbmRvYmoKeHJlZgowIDEwCjAwMDAwMDAwMDAgNjU1MzUgZiAKMDAwMDAwMjM3MyAwMDAwMCBuIAowMDAwMDAyNjQ5IDAwMDAwIG4gCjAwMDAwMDAwMTUgMDAwMDAgbiAKMDAwMDAwMjczNyAwMDAwMCBuIAowMDAwMDAyNjE0IDAwMDAwIG4gCjAwMDAwMDI4MDAgMDAwMDAgbiAKMDAwMDAwMjg1NCAwMDAwMCBuIAowMDAwMDAyODg2IDAwMDAwIG4gCjAwMDAwMDI5ODkgMDAwMDAgbiAKdHJhaWxlcgo8PC9JbmZvIDkgMCBSL0lEIFs8NTcyZTAxNTNhYmE2ZmQ2NzVjYWM0YzgwYWY2NGFkMjE+PDVlYjk1MTlhYzNjMTMxYmRlYWQ0ZTJjNWIwZmQ5NzVkPl0vUm9vdCA4IDAgUi9TaXplIDEwPj4Kc3RhcnR4cmVmCjMxOTgKJSVFT0YK";
		ContratoFakeRegisterRequest request = new Gson().fromJson(json, ContratoFakeRegisterRequest.class);
		log.info("Request {}", request);
		assertNotEquals(this.restTemplate.postForObject("http://localhost:" + port + "/api-contrato/viewPdfBase64",
				request, String.class), base64);
	}

}
