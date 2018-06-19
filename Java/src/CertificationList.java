import java.util.ArrayList;

public class CertificationList {
	//헌혈증리스트
	private ArrayList<Certification> CertList;
	
	public CertificationList()
	{
	}
	
	public void CertAdd(Certification cert)//헌혈증추가
	{
		CertList.add(cert);
	}
	
	public ArrayList<Certification> getCertList()//헌혈증리스트 가져오기
	{
		return CertList;
	}
	
	public Certification searchCert()//헌혈증 조회
	{
		Certification result=new Certification();//반환할 헌혈증
		
		return result;
	}
}
