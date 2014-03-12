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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.jws.WebService;

import se.riv.sll.mammography.screening.getexaminationresult.v1.rivtabp21.GetExaminationResultResponderInterface;
import se.riv.sll.mammography.screening.getexaminationresultresponder.v1.GetExaminationResultCollectionType;
import se.riv.sll.mammography.screening.getexaminationresultresponder.v1.GetExaminationResultResponseType;
import se.riv.sll.mammography.screening.getexaminationresultresponder.v1.GetExaminationResultType;
import se.riv.sll.mammography.screening.getexaminationresultresponder.v1.ResultResponseType;
import se.riv.sll.mammography.screening.v1.AddressType;
import se.riv.sll.mammography.screening.v1.ExaminerType;
import se.riv.sll.mammography.screening.v1.MalignancyResultType;
import se.riv.sll.mammography.screening.v1.ParticipationStatusEnum;
import se.riv.sll.mammography.screening.v1.PersonIdType;
import se.riv.sll.mammography.screening.v1.RadiologicalType;
import se.riv.sll.mammography.screening.v1.ResultType;
import se.riv.sll.mammography.screening.v1.ScreeningRoundType;
import se.riv.sll.mammography.screening.v1.ScreeningType;
import se.riv.sll.mammography.screening.v1.SelectionType;

@WebService
public class GetExaminationResultImpl implements GetExaminationResultResponderInterface {

	Random rand = new Random();

	@Override
	public GetExaminationResultResponseType getExaminationResult(String arg0, GetExaminationResultCollectionType arg1) {

		GetExaminationResultResponseType response = new GetExaminationResultResponseType();
		List<ResultResponseType> list = response.getResultResponse();

		if (arg1 != null && arg1.getGetExaminationResult() != null) {
			for (Iterator<GetExaminationResultType> iterator = arg1.getGetExaminationResult().iterator(); iterator
					.hasNext();) {
				GetExaminationResultType getExaminationResultType = (GetExaminationResultType) iterator.next();
				list.addAll(createRandomResultResponse(getExaminationResultType.getPatientId()));
			}
		}

		return response;
	}

	private List<ResultResponseType> createRandomResultResponse(PersonIdType patientId) {
		List<ResultResponseType> result = new ArrayList<ResultResponseType>();

		int iter = randInt(1, 10);
		for (int i = 0; i < iter; i++) {
			ResultResponseType response = new ResultResponseType();
			response.setPatientId(patientId);
			response.setName("VC Buren");
			AddressType address = new AddressType();
			address.setStreetName("Bättringsvägen 7k");
			address.setCity("Stockholm");
			address.setMunicipality("01");
			address.setState("01");
			address.setParish("01");
			address.setPostalNumber("123 45");
			response.setAddress(address);
			response.setBlocked(false);
			response.setNextScreeningDate("20140312");
			response.setScreeningRound(createRandomScreeningRound());
			result.add(response);

		}
		return result;
	}

	private ScreeningRoundType createRandomScreeningRound() {
		ScreeningRoundType result = new ScreeningRoundType();
		result.setRoundId("roundid #" + randInt(12, 45));
		result.setRoundNumber(randBigInt(1, 5));
		result.setDateOfInvitation("20140107");
		result.setUnit("unit A");
		result.setResult(createRandomResult());
		return result;
	}

	private ResultType createRandomResult() {
		ResultType result = new ResultType();
		// screeningResult
		ScreeningType screening = createRandomScreening();
		result.setScreeningResult(screening);
		createRandomRadiologicalResult(result.getRadiologicalResult());
		
		

		return result;
	}

	private void createRandomRadiologicalResult(List<RadiologicalType> radiologicalResult) {
		int iter = randInt(1, 3);
		for (int i = 0; i < iter; i++) {
			RadiologicalType radiological = new RadiologicalType();
			radiological.setDateOfExamination("Id" + i);
			radiological.setDateOfExamination("2014011" + i);
			radiological.setPatientDeclines(rand.nextBoolean());
			radiological.setExaminationCode("93850");
			radiological.setExaminationName("Ultraljudsledd punktion");
			MalignancyResultType mResult = new MalignancyResultType();
			mResult.setResultLeftBreast(randInt(0, 5));
			mResult.setResultRightBreast(randInt(0, 5));
			radiological.setDiagnosticCodes(mResult);
			radiologicalResult.add(radiological);
			
		}
		
	}

	private ScreeningType createRandomScreening() {
		ScreeningType screening = new ScreeningType();
		screening.setExaminationId("Id " + randInt(1, 5));
		screening.setDateOfExamination("20140120");
		screening.setMunicipality("01");
		screening.setParticipationStatus(ParticipationStatusEnum.D);
		MalignancyResultType mResult = new MalignancyResultType();
		mResult.setResultLeftBreast(randInt(0, 5));
		mResult.setResultRightBreast(randInt(0, 5));
		screening.setScreeningCodes(mResult);
		SelectionType selection = new SelectionType();
		selection.setResultLeftBreast(randInt(0, 2));
		selection.setResultRightBreast(randInt(0, 2));
		screening.setSelectionType(selection);
		screening.setOpinionResult(randInt(0, 3));
		screening.setDens(rand.nextBoolean());
		screening.setTechnicalRetake(rand.nextBoolean());
		ExaminerType examiner1 = new ExaminerType();
		examiner1.setId("#" + randInt(1, 10));
		examiner1.setName("Examiner " + examiner1.getId());
		screening.setExaminer1(examiner1);
		ExaminerType examiner2 = new ExaminerType();
		examiner2.setId("#" + randInt(11, 20));
		examiner2.setName("Examiner " + examiner2.getId());
		screening.setExaminer2(examiner2);
		return screening;
	}

	private int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		// Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	private BigInteger randBigInt(int min, int max) {
		return BigInteger.valueOf(randInt(min, max));
	}

}
