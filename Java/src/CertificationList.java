import java.util.ArrayList;

public class CertificationList {
	//����������Ʈ
	private ArrayList<Certification> CertList;
	
	public CertificationList()
	{
	}
	
	public void CertAdd(Certification cert)//�������߰�
	{
		CertList.add(cert);
	}
	
	public ArrayList<Certification> getCertList()//����������Ʈ ��������
	{
		return CertList;
	}
	
	public Certification searchCert()//������ ��ȸ
	{
		Certification result=new Certification();//��ȯ�� ������
		
		return result;
	}
}
