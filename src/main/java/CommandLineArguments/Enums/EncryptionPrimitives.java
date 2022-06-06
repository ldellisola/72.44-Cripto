package CommandLineArguments.Enums;

public enum EncryptionPrimitives {
    AES128 {
        @Override
        public String toString() {
            return "AES";
        }

        @Override
        public int KeyLength() {
            return 128;
        }
    },
    AES192 {
        @Override
        public String toString() {
            return "AES";
        }

        @Override
        public int KeyLength() {
            return 192;
        }
    },
    AES256 {
        @Override
        public String toString() {
            return "AES";
        }

        @Override
        public int KeyLength() {
            return 256;
        }
    },
    DES {
        @Override
        public String toString() {
            return "DES";
        }

        @Override
        public int KeyLength() {
            return 64;
        }
    };

    public int KeyLength() {
        return 0;
    }
}
