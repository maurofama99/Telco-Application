package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "service", schema = "db2_project_schema")
@NamedQuery(name = "Service.getAllServices", query = "SELECT r FROM Service r")
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn (name = "packageID",  referencedColumnName="ID")
    private TelcoPackage telcoPackage;

    private String type;

    @Basic(optional=true)
    private int NMin;
    @Basic(optional=true)
    private int NSMS;
    @Basic(optional=true)
    private int ExtraMinFee;
    @Basic(optional=true)
    private int ExtraSMSFee;
    @Basic(optional=true)
    private int NGiga;
    @Basic(optional = true)
    private int ExtraGigaFee;

    public Service() {
        this.type = "fixedphone";
    }

    public Service(int NMin, int NSMS, int extraMinFee, int extraSMSFee) {
        this.type = "mobilephone";
        this.NMin = NMin;
        this.NSMS = NSMS;
        ExtraMinFee = extraMinFee;
        ExtraSMSFee = extraSMSFee;
    }

    public Service(String type, int NGiga, int extraGigaFee) {
        this.type = type;
        this.NGiga = NGiga;
        ExtraGigaFee = extraGigaFee;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNMin() {
        return NMin;
    }

    public void setNMin(int NMin) {
        this.NMin = NMin;
    }

    public int getNSMS() {
        return NSMS;
    }

    public void setNSMS(int NSMS) {
        this.NSMS = NSMS;
    }

    public int getExtraMinFee() {
        return ExtraMinFee;
    }

    public void setExtraMinFee(int extraMinFee) {
        ExtraMinFee = extraMinFee;
    }

    public int getExtraSMSFee() {
        return ExtraSMSFee;
    }

    public void setExtraSMSFee(int extraSMSFee) {
        ExtraSMSFee = extraSMSFee;
    }

    public int getNGiga() {
        return NGiga;
    }

    public void setNGiga(int NGiga) {
        this.NGiga = NGiga;
    }

    public int getExtraGigaFee() {
        return ExtraGigaFee;
    }

    public void setExtraGigaFee(int extraGigaFee) {
        ExtraGigaFee = extraGigaFee;
    }

    public void setTelcoPackage(TelcoPackage telcoPackage) {
        this.telcoPackage = telcoPackage;
    }
}
