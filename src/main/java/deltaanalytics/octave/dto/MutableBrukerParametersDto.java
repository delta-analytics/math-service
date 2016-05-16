package deltaanalytics.octave.dto;

public class MutableBrukerParametersDto {
    private long id;
    private boolean currentDefault;
    private String APF;
    private String APT;
    private String AQM;
    private String BMS;
    private String BSF;
    private String CNM;
    private String DAP;
    private int DPA;
    private int DPO;
    private String EXP;
    private double HFQ;
    private double HFW;
    private double LFW;
    private double LFQ;
    private String NAM;
    private int NSS;
    private int NSR;
    private String OPF;
    private double PHR;
    private String PHZ;
    private String PLF;
    private String PTH;
    private String SAN;
    private String XPP;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCurrentDefault() {
        return currentDefault;
    }

    public void setCurrentDefault(boolean currentDefault) {
        this.currentDefault = currentDefault;
    }

    public String getAPF() {
        return APF;
    }

    public void setAPF(String APF) {
        this.APF = APF;
    }

    public String getAPT() {
        return APT;
    }

    public void setAPT(String APT) {
        this.APT = APT;
    }

    public String getAQM() {
        return AQM;
    }

    public void setAQM(String AQM) {
        this.AQM = AQM;
    }

    public String getBMS() {
        return BMS;
    }

    public void setBMS(String BMS) {
        this.BMS = BMS;
    }

    public String getBSF() {
        return BSF;
    }

    public void setBSF(String BSF) {
        this.BSF = BSF;
    }

    public String getCNM() {
        return CNM;
    }

    public void setCNM(String CNM) {
        this.CNM = CNM;
    }

    public String getDAP() {
        return DAP;
    }

    public void setDAP(String DAP) {
        this.DAP = DAP;
    }

    public int getDPA() {
        return DPA;
    }

    public void setDPA(int DPA) {
        this.DPA = DPA;
    }

    public int getDPO() {
        return DPO;
    }

    public void setDPO(int DPO) {
        this.DPO = DPO;
    }

    public String getEXP() {
        return EXP;
    }

    public void setEXP(String EXP) {
        this.EXP = EXP;
    }

    public double getHFQ() {
        return HFQ;
    }

    public void setHFQ(double HFQ) {
        this.HFQ = HFQ;
    }

    public double getLFW() {
        return LFW;
    }

    public void setLFW(double LFW) {
        this.LFW = LFW;
    }

    public String getNAM() {
        return NAM;
    }

    public void setNAM(String NAM) {
        this.NAM = NAM;
    }

    public int getNSS() {
        return NSS;
    }

    public void setNSS(int NSS) {
        this.NSS = NSS;
    }

    public int getNSR() {
        return NSR;
    }

    public void setNSR(int NSR) {
        this.NSR = NSR;
    }

    public String getOPF() {
        return OPF;
    }

    public void setOPF(String OPF) {
        this.OPF = OPF;
    }

    public double getPHR() {
        return PHR;
    }

    public void setPHR(double PHR) {
        this.PHR = PHR;
    }

    public String getPHZ() {
        return PHZ;
    }

    public void setPHZ(String PHZ) {
        this.PHZ = PHZ;
    }

    public String getPLF() {
        return PLF;
    }

    public void setPLF(String PLF) {
        this.PLF = PLF;
    }

    public String getPTH() {
        return PTH;
    }

    public void setPTH(String PTH) {
        this.PTH = PTH;
    }

    public String getSAN() {
        return SAN;
    }

    public void setSAN(String SAN) {
        this.SAN = SAN;
    }

    public String getXPP() {
        return XPP;
    }

    public void setXPP(String XPP) {
        this.XPP = XPP;
    }

    public double getHFW() {
        return HFW;
    }

    public void setHFW(double HFW) {
        this.HFW = HFW;
    }

    public double getLFQ() {
        return LFQ;
    }

    public void setLFQ(double LFQ) {
        this.LFQ = LFQ;
    }

    @Override
    public String toString() {
        return "MutableBrukerParametersDto{" +
                "id=" + id +
                ", currentDefault=" + currentDefault +
                ", APF='" + APF + '\'' +
                ", APT='" + APT + '\'' +
                ", AQM='" + AQM + '\'' +
                ", BMS='" + BMS + '\'' +
                ", BSF='" + BSF + '\'' +
                ", CNM='" + CNM + '\'' +
                ", DAP='" + DAP + '\'' +
                ", DPA=" + DPA +
                ", DPO=" + DPO +
                ", EXP='" + EXP + '\'' +
                ", HFQ=" + HFQ +
                ", HFW=" + HFW +
                ", LFW=" + LFW +
                ", LFQ=" + LFQ +
                ", NAM='" + NAM + '\'' +
                ", NSS=" + NSS +
                ", NSR=" + NSR +
                ", OPF='" + OPF + '\'' +
                ", PHR=" + PHR +
                ", PHZ='" + PHZ + '\'' +
                ", PLF='" + PLF + '\'' +
                ", PTH='" + PTH + '\'' +
                ", SAN='" + SAN + '\'' +
                ", XPP='" + XPP + '\'' +
                '}';
    }
}
