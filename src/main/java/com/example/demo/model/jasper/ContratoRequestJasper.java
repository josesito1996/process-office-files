package com.example.demo.model.jasper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@ToString
public class ContratoRequestJasper implements Serializable {

	private static final long serialVersionUID = -8648412630248457704L;

	private String idTemplate;

	private String razonSocial;

	private String nroPartida;

	private String oficinaRegistral;

	private String ruc;

	private String domicilioEmpleador;

	private String departamentoEmpleador;

	private String provinciaEmpleador;

	private String distritoEmpleador;

	private String nombreRepresentante;

	private String apellidoRepresentante;

	private String tipoDocumentoEmpleador;

	private String nroDocumentoEmpleador;

	private String nombresTrabajador;

	private String apellidosTrabajador;

	private String nacionalidadTrabajador;

	private String tipoDocumentoTrabajador;

	private String nroDocumentoTrabajador;

	private String domicilioTrabajador;

	private String departamentoTrabajador;

	private String provinciaTrabajador;

	private String distritoTrabajador;

	private String rubro;

	private String desempeño;

	private String experiencia;

	private String cargo;

	private String actividad;

	private String laboresAsignadas;

	private String areaLaboral;

	private String tipoContrato;

	private String fechaInicio;

	private String fechaHasta;

	private boolean esIndefinido;

	private Double sueldo;

	private String moneda;

	private String laboresAsignadas2;

	public static List<ContratoRequestJasper> build() {
		String json = "{\"idTemplate\":\"323232323\",\"razonSocial\":\"SUN INVERSIONES S.A.C\",\"nroPartida\":\"00.15\",\"oficinaRegistral\":\"OFICINA LIMA\",\"ruc\":\"20486229684\",\"domicilioEmpleador\":\"AV. NICOLAS DE PIEROLA NRO. 392 INT. 2DO CERCADO DE LIMA\",\"departamentoEmpleador\":\"Lima\",\"provinciaEmpleador\":\"Lima\",\"distritoEmpleador\":\"Breña\",\"nombreRepresentante\":\"WILMAN\",\"apellidoRepresentante\":\"SALVADOR URIOL\",\"tipoDocumentoEmpleador\":\"DNI\",\"nroDocumentoEmpleador\":\"05876985\",\"nombresTrabajador\":\"JOSE\",\"apellidosTrabajador\":\"CASTILLO CHALQUE\",\"nacionalidadTrabajador\":\"PERUANA\",\"tipoDocumentoTrabajador\":\"DNI\",\"nroDocumentoTrabajador\":\"75624412\",\"domicilioTrabajador\":\"JR RIO JORDAN MZ LOTE 1 MONTE DE SION\",\"departamentoTrabajador\":\"Lima\",\"provinciaTrabajador\":\"Lima\",\"distritoTrabajador\":\"San Juan de Lurigancho\",\"rubro\":\"Entretenimiento\",\"desempeño\":\"Desarrollador Jr.\",\"experiencia\":\"3 años\",\"cargo\":\"Soporte de Sistemas\",\"actividad\":\"Sistemas\",\"laboresAsignadas\":\"Dar soporte al area de oficina y sala\",\"areaLaboral\":\"Sistemas\",\"tipoContrato\":\"Indefinido\",\"fechaInicio\":\"2020-05-01\",\"fechaHasta\":\"2020-08-01\",\"esIndefinido\":true,\"sueldo\":2300,\"moneda\":\"S\",\"laboresAsignadas2\":\"Otras labores asignadas u.u\"}";
		@SuppressWarnings("unused")
		ContratoRequestJasper convertedObject = new Gson().fromJson(json, ContratoRequestJasper.class);
		return Arrays.asList(convertedObject);
	}

	public static void main(String... args) {
		System.out.println(build());
	}
}
