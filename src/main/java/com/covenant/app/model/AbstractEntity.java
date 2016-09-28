package com.covenant.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;


@MappedSuperclass
public class AbstractEntity {
	
	
	
	private Long pkey;
	private Boolean deleted = false;
    private String createdBy;
    private Date createdAt;
    private String changedBy;
    private Date changedAt; 	
	private Long version;	
	private String uuid;
	
	
	 public AbstractEntity()
	    {
	    	this.createdAt=  new Date();
	    	this.deleted = false;
	    	
	    }
	 
		@Id
	    @GeneratedValue
		@Column(name="pkey")
		public Long getPkey() {
			return pkey;
		}
		public void setPkey(Long pkey) {
			this.pkey = pkey;
		}
		
	    @Column(name="deleted")
		public Boolean getDeleted() {
			return deleted;
		}
	    
		public void setDeleted(Boolean deleted) {
			this.deleted = deleted;
		}

	    @Column(name="created_by")
	    public String getCreatedBy() {
	        return createdBy;
	    }

	    public void setCreatedBy(String creator) {
	        this.createdBy = creator;
	    }

	    @Column(name="created_at")
	    public Date getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(Date created) {
	        this.createdAt = created;
	    }

	    @Column(name="changed_by")
	    public String getChangedBy() {
	        return changedBy;
	    }

	    public void setChangedBy(String changer) {
	        this.changedBy = changer;
	    }

	    @Column(name="changed_at")
	    public Date getChangedAt() {
	        return changedAt;
	    }

	    public void setChangedAt(Date changed) {
	        this.changedAt = changed;
	    }
	    
	    @Version
	    @Column(name="version")
		public Long getVersion() {
			return version;
		}
		public void setVersion(Long version) {
			this.version = version;
		}

	    @GeneratedValue(generator="system-uuid")
		@GenericGenerator(name="system-uuid", strategy = "uuid")
		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
	  
		
	
	@Override
	public boolean equals(Object o){
		
		if(this == o) return true;
		
		if( (o == null) || o.getClass() != getClass() ) return false;
		
		AbstractEntity that = (AbstractEntity)o;
		
		if( (getUuid() != null)  && that.getUuid() != null) 
				return getUuid().equals(that.getUuid());
		else
			 return false;
					
	}
	@Override
	public int hashCode(){
		return (getUuid() != null) ? getUuid().hashCode() : 0;
	}
	
	
	
	

}
