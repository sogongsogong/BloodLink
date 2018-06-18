import java.util.ArrayList;

public class CertificationList {
	//ÇåÇ÷Áõ¸®½ºÆ®
	private ArrayList<Certification> CertList;
	
	public CertificationList()
	{
	}
	
	public void CertAdd(Certification cert)//ÇåÇ÷ÁõÃß°¡
	{
		CertList.add(cert);
	}
	
	public ArrayList<Certification> getCertList()//ÇåÇ÷Áõ¸®½ºÆ® °¡Á®¿À±â
	{
		return CertList;
	}
	
	public Certification searchCert()//ÇåÇ÷Áõ Á¶È¸
	{
		Certification result=new Certification();//¹İÈ¯ÇÒ ÇåÇ÷Áõ
		
		return result;
	}
}
