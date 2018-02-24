
/**
 * RequestParam.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:48:01 BST)
 */

package cn.migu.income.webservices;

/**
 * RequestParam bean class
 */
@SuppressWarnings({ "unchecked", "unused" })

public class RequestParam implements org.apache.axis2.databinding.ADBBean {
	/*
	 * This type was generated from the piece of schema that had name =
	 * RequestParam Namespace URI =
	 * http://contractBudget.interfaceserver.ws.eas.zte.com/xsd Namespace Prefix
	 * = ns1
	 */

	/**
	 * field for SystemSource
	 */

	protected java.lang.String localSystemSource;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localSystemSourceTracker = false;

	public boolean isSystemSourceSpecified() {
		return localSystemSourceTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getSystemSource() {
		return localSystemSource;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            SystemSource
	 */
	public void setSystemSource(java.lang.String param) {
		localSystemSourceTracker = true;

		this.localSystemSource = param;

	}

	/**
	 * field for BudgetYear
	 */

	protected java.lang.String localBudgetYear;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localBudgetYearTracker = false;

	public boolean isBudgetYearSpecified() {
		return localBudgetYearTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getBudgetYear() {
		return localBudgetYear;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            BudgetYear
	 */
	public void setBudgetYear(java.lang.String param) {
		localBudgetYearTracker = true;

		this.localBudgetYear = param;

	}

	/**
	 * field for BudgetDeptCode
	 */

	protected java.lang.String localBudgetDeptCode;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localBudgetDeptCodeTracker = false;

	public boolean isBudgetDeptCodeSpecified() {
		return localBudgetDeptCodeTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getBudgetDeptCode() {
		return localBudgetDeptCode;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            BudgetDeptCode
	 */
	public void setBudgetDeptCode(java.lang.String param) {
		localBudgetDeptCodeTracker = true;

		this.localBudgetDeptCode = param;

	}

	/**
	 * field for BudgetResultId
	 */

	protected java.lang.String localBudgetResultId;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localBudgetResultIdTracker = false;

	public boolean isBudgetResultIdSpecified() {
		return localBudgetResultIdTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getBudgetResultId() {
		return localBudgetResultId;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            BudgetResultId
	 */
	public void setBudgetResultId(java.lang.String param) {
		localBudgetResultIdTracker = true;

		this.localBudgetResultId = param;

	}

	/**
	 * field for CurrentPage
	 */

	protected int localCurrentPage;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localCurrentPageTracker = false;

	public boolean isCurrentPageSpecified() {
		return localCurrentPageTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return int
	 */
	public int getCurrentPage() {
		return localCurrentPage;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            CurrentPage
	 */
	public void setCurrentPage(int param) {

		// setting primitive attribute tracker to true
		localCurrentPageTracker = param != java.lang.Integer.MIN_VALUE;

		this.localCurrentPage = param;

	}

	/**
	 * field for PageSize
	 */

	protected int localPageSize;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localPageSizeTracker = false;

	public boolean isPageSizeSpecified() {
		return localPageSizeTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return localPageSize;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            PageSize
	 */
	public void setPageSize(int param) {

		// setting primitive attribute tracker to true
		localPageSizeTracker = param != java.lang.Integer.MIN_VALUE;

		this.localPageSize = param;

	}

	/**
	 * field for TotalPage
	 */

	protected int localTotalPage;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localTotalPageTracker = false;

	public boolean isTotalPageSpecified() {
		return localTotalPageTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return int
	 */
	public int getTotalPage() {
		return localTotalPage;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            TotalPage
	 */
	public void setTotalPage(int param) {

		// setting primitive attribute tracker to true
		localTotalPageTracker = param != java.lang.Integer.MIN_VALUE;

		this.localTotalPage = param;

	}

	/**
	 * field for TotalRecord
	 */

	protected int localTotalRecord;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localTotalRecordTracker = false;

	public boolean isTotalRecordSpecified() {
		return localTotalRecordTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return int
	 */
	public int getTotalRecord() {
		return localTotalRecord;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            TotalRecord
	 */
	public void setTotalRecord(int param) {

		// setting primitive attribute tracker to true
		localTotalRecordTracker = param != java.lang.Integer.MIN_VALUE;

		this.localTotalRecord = param;

	}

	/**
	 *
	 * @param parentQName
	 * @param factory
	 * @return org.apache.axiom.om.OMElement
	 */
	public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,
			final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException {

		return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this, parentQName));

	}

	public void serialize(final javax.xml.namespace.QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {
		serialize(parentQName, xmlWriter, false);
	}

	public void serialize(final javax.xml.namespace.QName parentQName, javax.xml.stream.XMLStreamWriter xmlWriter,
			boolean serializeType)
			throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException {

		java.lang.String prefix = null;
		java.lang.String namespace = null;

		prefix = parentQName.getPrefix();
		namespace = parentQName.getNamespaceURI();
		writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);

		if (serializeType) {

			java.lang.String namespacePrefix = registerPrefix(xmlWriter,
					"http://contractBudget.interfaceserver.ws.eas.zte.com/xsd");
			if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)) {
				writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type",
						namespacePrefix + ":RequestParam", xmlWriter);
			} else {
				writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "RequestParam", xmlWriter);
			}

		}
		if (localSystemSourceTracker) {
			namespace = "http://contractBudget.interfaceserver.ws.eas.zte.com/xsd";
			writeStartElement(null, namespace, "systemSource", xmlWriter);

			if (localSystemSource == null) {
				// write the nil attribute

				writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);

			} else {

				xmlWriter.writeCharacters(localSystemSource);

			}

			xmlWriter.writeEndElement();
		}
		if (localBudgetYearTracker) {
			namespace = "http://contractBudget.interfaceserver.ws.eas.zte.com/xsd";
			writeStartElement(null, namespace, "budgetYear", xmlWriter);

			if (localBudgetYear == null) {
				// write the nil attribute

				writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);

			} else {

				xmlWriter.writeCharacters(localBudgetYear);

			}

			xmlWriter.writeEndElement();
		}
		if (localBudgetDeptCodeTracker) {
			namespace = "http://contractBudget.interfaceserver.ws.eas.zte.com/xsd";
			writeStartElement(null, namespace, "budgetDeptCode", xmlWriter);

			if (localBudgetDeptCode == null) {
				// write the nil attribute

				writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);

			} else {

				xmlWriter.writeCharacters(localBudgetDeptCode);

			}

			xmlWriter.writeEndElement();
		}
		if (localBudgetResultIdTracker) {
			namespace = "http://contractBudget.interfaceserver.ws.eas.zte.com/xsd";
			writeStartElement(null, namespace, "budgetResultId", xmlWriter);

			if (localBudgetResultId == null) {
				// write the nil attribute

				writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", xmlWriter);

			} else {

				xmlWriter.writeCharacters(localBudgetResultId);

			}

			xmlWriter.writeEndElement();
		}
		if (localCurrentPageTracker) {
			namespace = "http://contractBudget.interfaceserver.ws.eas.zte.com/xsd";
			writeStartElement(null, namespace, "currentPage", xmlWriter);

			if (localCurrentPage == java.lang.Integer.MIN_VALUE) {

				throw new org.apache.axis2.databinding.ADBException("currentPage cannot be null!!");

			} else {
				xmlWriter.writeCharacters(
						org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrentPage));
			}

			xmlWriter.writeEndElement();
		}
		if (localPageSizeTracker) {
			namespace = "http://contractBudget.interfaceserver.ws.eas.zte.com/xsd";
			writeStartElement(null, namespace, "pageSize", xmlWriter);

			if (localPageSize == java.lang.Integer.MIN_VALUE) {

				throw new org.apache.axis2.databinding.ADBException("pageSize cannot be null!!");

			} else {
				xmlWriter.writeCharacters(
						org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPageSize));
			}

			xmlWriter.writeEndElement();
		}
		if (localTotalPageTracker) {
			namespace = "http://contractBudget.interfaceserver.ws.eas.zte.com/xsd";
			writeStartElement(null, namespace, "totalPage", xmlWriter);

			if (localTotalPage == java.lang.Integer.MIN_VALUE) {

				throw new org.apache.axis2.databinding.ADBException("totalPage cannot be null!!");

			} else {
				xmlWriter.writeCharacters(
						org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotalPage));
			}

			xmlWriter.writeEndElement();
		}
		if (localTotalRecordTracker) {
			namespace = "http://contractBudget.interfaceserver.ws.eas.zte.com/xsd";
			writeStartElement(null, namespace, "totalRecord", xmlWriter);

			if (localTotalRecord == java.lang.Integer.MIN_VALUE) {

				throw new org.apache.axis2.databinding.ADBException("totalRecord cannot be null!!");

			} else {
				xmlWriter.writeCharacters(
						org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotalRecord));
			}

			xmlWriter.writeEndElement();
		}
		xmlWriter.writeEndElement();

	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {
		if (namespace.equals("http://contractBudget.interfaceserver.ws.eas.zte.com/xsd")) {
			return "ns1";
		}
		return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
	}

	/**
	 * Utility method to write an element start tag.
	 */
	private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
			javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
		java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
		if (writerPrefix != null) {
			xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
		} else {
			if (namespace.length() == 0) {
				prefix = "";
			} else if (prefix == null) {
				prefix = generatePrefix(namespace);
			}

			xmlWriter.writeStartElement(prefix, localPart, namespace);
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
	}

	/**
	 * Util method to write an attribute with the ns prefix
	 */
	private void writeAttribute(java.lang.String prefix, java.lang.String namespace, java.lang.String attName,
			java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
		if (writerPrefix != null) {
			xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
		} else {
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
			xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
		}
	}

	/**
	 * Util method to write an attribute without the ns prefix
	 */
	private void writeAttribute(java.lang.String namespace, java.lang.String attName, java.lang.String attValue,
			javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
		if (namespace.equals("")) {
			xmlWriter.writeAttribute(attName, attValue);
		} else {
			xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace, attName, attValue);
		}
	}

	/**
	 * Util method to write an attribute without the ns prefix
	 */
	private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
			javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {

		java.lang.String attributeNamespace = qname.getNamespaceURI();
		java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
		if (attributePrefix == null) {
			attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
		}
		java.lang.String attributeValue;
		if (attributePrefix.trim().length() > 0) {
			attributeValue = attributePrefix + ":" + qname.getLocalPart();
		} else {
			attributeValue = qname.getLocalPart();
		}

		if (namespace.equals("")) {
			xmlWriter.writeAttribute(attName, attributeValue);
		} else {
			registerPrefix(xmlWriter, namespace);
			xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
		}
	}

	/**
	 * method to handle Qnames
	 */

	private void writeQName(javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String namespaceURI = qname.getNamespaceURI();
		if (namespaceURI != null) {
			java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
			if (prefix == null) {
				prefix = generatePrefix(namespaceURI);
				xmlWriter.writeNamespace(prefix, namespaceURI);
				xmlWriter.setPrefix(prefix, namespaceURI);
			}

			if (prefix.trim().length() > 0) {
				xmlWriter.writeCharacters(
						prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			} else {
				// i.e this is the default namespace
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}

		} else {
			xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
		}
	}

	private void writeQNames(javax.xml.namespace.QName[] qnames, javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {

		if (qnames != null) {
			// we have to store this data until last moment since it is not
			// possible to write any
			// namespace data after writing the charactor data
			java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
			java.lang.String namespaceURI = null;
			java.lang.String prefix = null;

			for (int i = 0; i < qnames.length; i++) {
				if (i > 0) {
					stringToWrite.append(" ");
				}
				namespaceURI = qnames[i].getNamespaceURI();
				if (namespaceURI != null) {
					prefix = xmlWriter.getPrefix(namespaceURI);
					if ((prefix == null) || (prefix.length() == 0)) {
						prefix = generatePrefix(namespaceURI);
						xmlWriter.writeNamespace(prefix, namespaceURI);
						xmlWriter.setPrefix(prefix, namespaceURI);
					}

					if (prefix.trim().length() > 0) {
						stringToWrite.append(prefix).append(":")
								.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				} else {
					stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
				}
			}
			xmlWriter.writeCharacters(stringToWrite.toString());
		}

	}

	/**
	 * Register a namespace prefix
	 */
	private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String prefix = xmlWriter.getPrefix(namespace);
		if (prefix == null) {
			prefix = generatePrefix(namespace);
			javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
			while (true) {
				java.lang.String uri = nsContext.getNamespaceURI(prefix);
				if (uri == null || uri.length() == 0) {
					break;
				}
				prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
			}
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
		return prefix;
	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {
		private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

		/**
		 * static method to create the object Precondition: If this object is an
		 * element, the current or next start element starts this object and any
		 * intervening reader events are ignorable If this object is not an
		 * element, it is a complex type and the reader is at the event just
		 * after the outer start element Postcondition: If this object is an
		 * element, the reader is positioned at its end element If this object
		 * is a complex type, the reader is positioned at the end element of its
		 * outer element
		 */
		public static RequestParam parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
			RequestParam object = new RequestParam();

			int event;
			javax.xml.namespace.QName currentQName = null;
			java.lang.String nillableValue = null;
			java.lang.String prefix = "";
			java.lang.String namespaceuri = "";
			try {

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				currentQName = reader.getName();

				if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
					java.lang.String fullTypeName = reader
							.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
					if (fullTypeName != null) {
						java.lang.String nsPrefix = null;
						if (fullTypeName.indexOf(":") > -1) {
							nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":"));
						}
						nsPrefix = nsPrefix == null ? "" : nsPrefix;

						java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);

						if (!"RequestParam".equals(type)) {
							// find namespace for the prefix
							java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
							return (RequestParam) cn.migu.income.webservices.ExtensionMapper
									.getTypeObject(nsUri, type, reader);
						}

					}

				}

				// Note all attributes that were handled. Used to differ normal
				// attributes
				// from anyAttributes.
				java.util.Vector handledAttributes = new java.util.Vector();

				reader.next();

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName("http://contractBudget.interfaceserver.ws.eas.zte.com/xsd",
								"systemSource").equals(reader.getName())
						|| new javax.xml.namespace.QName("", "systemSource").equals(reader.getName())) {

					nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
					if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {

						java.lang.String content = reader.getElementText();

						object.setSystemSource(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

					} else {

						reader.getElementText(); // throw away text nodes if
													// any.
					}

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName("http://contractBudget.interfaceserver.ws.eas.zte.com/xsd",
								"budgetYear").equals(reader.getName())
						|| new javax.xml.namespace.QName("", "budgetYear").equals(reader.getName())) {

					nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
					if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {

						java.lang.String content = reader.getElementText();

						object.setBudgetYear(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

					} else {

						reader.getElementText(); // throw away text nodes if
													// any.
					}

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName("http://contractBudget.interfaceserver.ws.eas.zte.com/xsd",
								"budgetDeptCode").equals(reader.getName())
						|| new javax.xml.namespace.QName("", "budgetDeptCode").equals(reader.getName())) {

					nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
					if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {

						java.lang.String content = reader.getElementText();

						object.setBudgetDeptCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

					} else {

						reader.getElementText(); // throw away text nodes if
													// any.
					}

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName("http://contractBudget.interfaceserver.ws.eas.zte.com/xsd",
								"budgetResultId").equals(reader.getName())
						|| new javax.xml.namespace.QName("", "budgetResultId").equals(reader.getName())) {

					nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
					if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {

						java.lang.String content = reader.getElementText();

						object.setBudgetResultId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

					} else {

						reader.getElementText(); // throw away text nodes if
													// any.
					}

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName("http://contractBudget.interfaceserver.ws.eas.zte.com/xsd",
								"currentPage").equals(reader.getName())
						|| new javax.xml.namespace.QName("", "currentPage").equals(reader.getName())) {

					nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "currentPage" + "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setCurrentPage(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

					reader.next();

				} // End of if for expected property start element

				else {

					object.setCurrentPage(java.lang.Integer.MIN_VALUE);

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName("http://contractBudget.interfaceserver.ws.eas.zte.com/xsd",
								"pageSize").equals(reader.getName())
						|| new javax.xml.namespace.QName("", "pageSize").equals(reader.getName())) {

					nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "pageSize" + "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setPageSize(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

					reader.next();

				} // End of if for expected property start element

				else {

					object.setPageSize(java.lang.Integer.MIN_VALUE);

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName("http://contractBudget.interfaceserver.ws.eas.zte.com/xsd",
								"totalPage").equals(reader.getName())
						|| new javax.xml.namespace.QName("", "totalPage").equals(reader.getName())) {

					nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "totalPage" + "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setTotalPage(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

					reader.next();

				} // End of if for expected property start element

				else {

					object.setTotalPage(java.lang.Integer.MIN_VALUE);

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName("http://contractBudget.interfaceserver.ws.eas.zte.com/xsd",
								"totalRecord").equals(reader.getName())
						|| new javax.xml.namespace.QName("", "totalRecord").equals(reader.getName())) {

					nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "totalRecord" + "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setTotalRecord(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));

					reader.next();

				} // End of if for expected property start element

				else {

					object.setTotalRecord(java.lang.Integer.MIN_VALUE);

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement())
					// 2 - A start element we are not expecting indicates a
					// trailing invalid property

					throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());

			} catch (javax.xml.stream.XMLStreamException e) {
				throw new java.lang.Exception(e);
			}

			return object;
		}

	}// end of factory class

}
