/**
 * Copyright 2013 Stockholm Läns Landsting
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *
 *   Boston, MA 02111-1307  USA
 */
package se.skltp.sll.mammography.screening.getexaminationresult.v1;

import java.net.URL;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;

public class Launcher {

	protected Launcher(String address) throws Exception {
		System.out.println("Starting GetExaminationResult testproducer");

		// Loads a cxf configuration file to use
		final SpringBusFactory bf = new SpringBusFactory();
		final URL busFile = this.getClass().getClassLoader().getResource("cxf-producer.xml");
		final Bus bus = bf.createBus(busFile.toString());

		SpringBusFactory.setDefaultBus(bus);
		final Object implementor = new GetExaminationResultImpl();
		Endpoint.publish(address, implementor);
	}

	public static void main(String[] args) throws Exception {
        String address = "http://localhost:20001/testproducer/sll/mammography/screening/GetExaminationResult/1/rivtabp21";
        new Launcher(address);
        System.out.println("Producer ready and listening on address " + address);

		Thread.sleep(5 * 60 * 1000);
		System.out.println("Producer exiting");
		System.exit(0);

	}
}