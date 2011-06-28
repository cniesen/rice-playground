package org.kuali.rice.kim.impl.identity.type

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import org.apache.commons.collections.CollectionUtils
import org.hibernate.annotations.Type
import org.kuali.rice.kim.api.identity.EntityUtils
import org.kuali.rice.kim.api.identity.address.EntityAddress
import org.kuali.rice.kim.api.identity.email.EntityEmail
import org.kuali.rice.kim.api.identity.phone.EntityPhone
import org.kuali.rice.kim.api.identity.type.EntityTypeData
import org.kuali.rice.kim.api.identity.type.EntityTypeDataContract
import org.kuali.rice.kim.impl.identity.EntityTypeBo
import org.kuali.rice.kim.impl.identity.address.EntityAddressBo
import org.kuali.rice.kim.impl.identity.email.EntityEmailBo
import org.kuali.rice.kim.impl.identity.phone.EntityPhoneBo
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase
import org.kuali.rice.kim.api.identity.type.EntityTypeDataDefault

@Entity
@IdClass(EntityTypeDataId.class)
@Table(name = "KRIM_ENTITY_ENT_TYP_T")
public class EntityTypeDataBo extends PersistableBusinessObjectBase implements EntityTypeDataContract {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ENTITY_ID")
    String entityId;

    @Id
    @Column(name = "ENT_TYP_CD")
    String entityTypeCode;

    @ManyToOne(targetEntity = EntityTypeBo.class, fetch = FetchType.EAGER, cascade = [])
    @JoinColumn(name = "ENT_TYP_CD", insertable = false, updatable = false)
    EntityTypeBo entityType;

    @OneToMany(targetEntity = EntityTypeDataBo.class, fetch = FetchType.EAGER, cascade = [ CascadeType.ALL ])
    @JoinColumns([
        @JoinColumn(name="ENTITY_ID", insertable = false, updatable = false),
        @JoinColumn(name="ENT_TYP_CD", insertable = false, updatable = false)
    ])
    List<EntityEmailBo> emailAddresses;

    @OneToMany(targetEntity = EntityPhoneBo.class, fetch = FetchType.EAGER, cascade = [ CascadeType.ALL ])
    @JoinColumns([
        @JoinColumn(name="ENTITY_ID", insertable = false, updatable = false),
        @JoinColumn(name="ENT_TYP_CD", insertable = false, updatable = false)
    ])
    List<EntityPhoneBo> phoneNumbers

    @OneToMany(targetEntity = EntityAddressBo.class, fetch = FetchType.EAGER, cascade = [ CascadeType.ALL ])
    @JoinColumns([
        @JoinColumn(name="ENTITY_ID", insertable = false, updatable = false),
        @JoinColumn(name="ENT_TYP_CD", insertable = false, updatable = false)
    ])
    List<EntityAddressBo> addresses;

    @Type(type="yes_no")
    @Column(name="ACTV_IND")
    boolean active;

         /*
       * Converts a mutable EntityTypeDataBo to an immutable EntityTypeData representation.
       * @param bo
       * @return an immutable EntityTypeData
       */
      static EntityTypeData to(EntityTypeDataBo bo) {
        if (bo == null) { return null }
        return EntityTypeData.Builder.create(bo).build()
      }

      static EntityTypeDataDefault toDefault(EntityTypeDataBo bo) {
          if (bo == null) { return null }
          return new EntityTypeDataDefault(bo.getEntityTypeCode(),
                                        EntityAddressBo.to(bo.getDefaultAddress()),
                                        EntityTypeDataBo.to(bo.getDefaultEmailAddress()),
                                        EntityPhoneBo.to(bo.getDefaultPhoneNumber()))
      }

      /**
       * Creates a EntityTypeDataBo business object from an immutable representation of a EntityTypeData.
       * @param an immutable EntityTypeData
       * @return a EntityTypeDataBo
       */
      static EntityTypeDataBo from(EntityTypeData immutable) {
        if (immutable == null) {return null}

        EntityTypeDataBo bo = new EntityTypeDataBo()
        bo.active = immutable.active

        bo.entityId = immutable.entityId
        bo.entityTypeCode = immutable.entityTypeCode
        bo.addresses = new ArrayList<EntityAddressBo>()
        if (CollectionUtils.isNotEmpty(immutable.addresses)) {
            for (EntityAddress address : immutable.addresses) {
                bo.addresses.add(EntityAddressBo.from(address))
            }
        }
        bo.phoneNumbers = new ArrayList<EntityPhoneBo>()
        if (CollectionUtils.isNotEmpty(immutable.phoneNumbers)) {
            for (EntityPhone phone : immutable.phoneNumbers) {
                bo.phoneNumbers.add(EntityPhoneBo.from(phone))
            }
        }
        bo.emailAddresses = new ArrayList<EntityTypeDataBo>()
        if (CollectionUtils.isNotEmpty(immutable.emailAddresses)) {
            for (EntityTypeData email : immutable.emailAddresses) {
                bo.emailAddresses.add(EntityTypeDataBo.from(email))
            }
        }
        bo.versionNumber = immutable.versionNumber
        bo.objectId = immutable.objectId

        return bo;
      }

    EntityAddressBo getDefaultAddress() {
        return EntityUtils.getDefaultItem(this.addresses);
    }
    EntityEmailBo getDefaultEmailAddress() {
        return EntityUtils.getDefaultItem(this.emailAddresses);
    }
    EntityPhoneBo getDefaultPhoneNumber() {
        return EntityUtils.getDefaultItem(this.phoneNumbers);
    }

    EntityTypeBo getEntityType() {
        return this.entityType
    }

}
