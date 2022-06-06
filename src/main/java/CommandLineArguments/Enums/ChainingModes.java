package CommandLineArguments.Enums;

public enum ChainingModes {
    CBC {
        @Override
        public String toString() {
            return "CBC";
        }

        @Override
        public String getPadding() {
            return "PKCS5Padding";
        }
    },
    ECB {
        @Override
        public String toString() {
            return "ECB";
        }

        @Override
        public String getPadding() {
            return "PKCS5Padding";
        }

        @Override
        public boolean usesIV() {
            return false;
        }
    },
    CFB {
        @Override
        public String toString() {
            return "CFB";
        }
    },
    OFB {
        @Override
        public String toString() {
            return "OFB";
        }
    };

    public String getPadding() {
        return "NoPadding";
    }

    public boolean usesIV() {
        return true;
    }
}
