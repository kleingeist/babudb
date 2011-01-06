// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: interface/Common.proto

package org.xtreemfs.babudb.pbrpc;

public final class Common {
  private Common() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class emptyRequest extends
      com.google.protobuf.GeneratedMessage {
    // Use emptyRequest.newBuilder() to construct.
    private emptyRequest() {
      initFields();
    }
    private emptyRequest(boolean noInit) {}
    
    private static final emptyRequest defaultInstance;
    public static emptyRequest getDefaultInstance() {
      return defaultInstance;
    }
    
    public emptyRequest getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.xtreemfs.babudb.pbrpc.Common.internal_static_org_xtreemfs_pbrpc_emptyRequest_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.xtreemfs.babudb.pbrpc.Common.internal_static_org_xtreemfs_pbrpc_emptyRequest_fieldAccessorTable;
    }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(org.xtreemfs.babudb.pbrpc.Common.emptyRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private org.xtreemfs.babudb.pbrpc.Common.emptyRequest result;
      
      // Construct using org.xtreemfs.babudb.pbrpc.Common.emptyRequest.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new org.xtreemfs.babudb.pbrpc.Common.emptyRequest();
        return builder;
      }
      
      protected org.xtreemfs.babudb.pbrpc.Common.emptyRequest internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new org.xtreemfs.babudb.pbrpc.Common.emptyRequest();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.xtreemfs.babudb.pbrpc.Common.emptyRequest.getDescriptor();
      }
      
      public org.xtreemfs.babudb.pbrpc.Common.emptyRequest getDefaultInstanceForType() {
        return org.xtreemfs.babudb.pbrpc.Common.emptyRequest.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public org.xtreemfs.babudb.pbrpc.Common.emptyRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private org.xtreemfs.babudb.pbrpc.Common.emptyRequest buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public org.xtreemfs.babudb.pbrpc.Common.emptyRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        org.xtreemfs.babudb.pbrpc.Common.emptyRequest returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.xtreemfs.babudb.pbrpc.Common.emptyRequest) {
          return mergeFrom((org.xtreemfs.babudb.pbrpc.Common.emptyRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(org.xtreemfs.babudb.pbrpc.Common.emptyRequest other) {
        if (other == org.xtreemfs.babudb.pbrpc.Common.emptyRequest.getDefaultInstance()) return this;
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
          }
        }
      }
      
      
      // @@protoc_insertion_point(builder_scope:org.xtreemfs.pbrpc.emptyRequest)
    }
    
    static {
      defaultInstance = new emptyRequest(true);
      org.xtreemfs.babudb.pbrpc.Common.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:org.xtreemfs.pbrpc.emptyRequest)
  }
  
  public static final class emptyResponse extends
      com.google.protobuf.GeneratedMessage {
    // Use emptyResponse.newBuilder() to construct.
    private emptyResponse() {
      initFields();
    }
    private emptyResponse(boolean noInit) {}
    
    private static final emptyResponse defaultInstance;
    public static emptyResponse getDefaultInstance() {
      return defaultInstance;
    }
    
    public emptyResponse getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.xtreemfs.babudb.pbrpc.Common.internal_static_org_xtreemfs_pbrpc_emptyResponse_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.xtreemfs.babudb.pbrpc.Common.internal_static_org_xtreemfs_pbrpc_emptyResponse_fieldAccessorTable;
    }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.xtreemfs.babudb.pbrpc.Common.emptyResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(org.xtreemfs.babudb.pbrpc.Common.emptyResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private org.xtreemfs.babudb.pbrpc.Common.emptyResponse result;
      
      // Construct using org.xtreemfs.babudb.pbrpc.Common.emptyResponse.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new org.xtreemfs.babudb.pbrpc.Common.emptyResponse();
        return builder;
      }
      
      protected org.xtreemfs.babudb.pbrpc.Common.emptyResponse internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new org.xtreemfs.babudb.pbrpc.Common.emptyResponse();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.xtreemfs.babudb.pbrpc.Common.emptyResponse.getDescriptor();
      }
      
      public org.xtreemfs.babudb.pbrpc.Common.emptyResponse getDefaultInstanceForType() {
        return org.xtreemfs.babudb.pbrpc.Common.emptyResponse.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public org.xtreemfs.babudb.pbrpc.Common.emptyResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private org.xtreemfs.babudb.pbrpc.Common.emptyResponse buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public org.xtreemfs.babudb.pbrpc.Common.emptyResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        org.xtreemfs.babudb.pbrpc.Common.emptyResponse returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.xtreemfs.babudb.pbrpc.Common.emptyResponse) {
          return mergeFrom((org.xtreemfs.babudb.pbrpc.Common.emptyResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(org.xtreemfs.babudb.pbrpc.Common.emptyResponse other) {
        if (other == org.xtreemfs.babudb.pbrpc.Common.emptyResponse.getDefaultInstance()) return this;
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
          }
        }
      }
      
      
      // @@protoc_insertion_point(builder_scope:org.xtreemfs.pbrpc.emptyResponse)
    }
    
    static {
      defaultInstance = new emptyResponse(true);
      org.xtreemfs.babudb.pbrpc.Common.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:org.xtreemfs.pbrpc.emptyResponse)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_org_xtreemfs_pbrpc_emptyRequest_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_org_xtreemfs_pbrpc_emptyRequest_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_org_xtreemfs_pbrpc_emptyResponse_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_org_xtreemfs_pbrpc_emptyResponse_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026interface/Common.proto\022\022org.xtreemfs.p" +
      "brpc\"\016\n\014emptyRequest\"\017\n\remptyResponseB\033\n" +
      "\031org.xtreemfs.babudb.pbrpc"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_org_xtreemfs_pbrpc_emptyRequest_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_org_xtreemfs_pbrpc_emptyRequest_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_org_xtreemfs_pbrpc_emptyRequest_descriptor,
              new java.lang.String[] { },
              org.xtreemfs.babudb.pbrpc.Common.emptyRequest.class,
              org.xtreemfs.babudb.pbrpc.Common.emptyRequest.Builder.class);
          internal_static_org_xtreemfs_pbrpc_emptyResponse_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_org_xtreemfs_pbrpc_emptyResponse_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_org_xtreemfs_pbrpc_emptyResponse_descriptor,
              new java.lang.String[] { },
              org.xtreemfs.babudb.pbrpc.Common.emptyResponse.class,
              org.xtreemfs.babudb.pbrpc.Common.emptyResponse.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  public static void internalForceInit() {}
  
  // @@protoc_insertion_point(outer_class_scope)
}