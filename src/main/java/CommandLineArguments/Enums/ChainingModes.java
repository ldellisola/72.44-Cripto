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

        @Override
        public boolean usesIV() {
            return true;
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
            return "CFB8";
        }

        @Override
        public String getPadding() {
            return "NoPadding";
        }

        @Override
        public boolean usesIV() {
            return true;
        }
    },
    OFB {
        @Override
        public String toString() {
            return "OFB";
        }

        @Override
        public String getPadding() {
            return "NoPadding";
        }

        @Override
        public boolean usesIV() {
            return true;
        }
    };

    public String getPadding() {
        return "NoPadding";
    }

    public boolean usesIV() {
        return true;
    }
}
